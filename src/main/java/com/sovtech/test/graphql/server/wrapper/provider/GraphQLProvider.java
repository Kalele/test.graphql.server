package com.sovtech.test.graphql.server.wrapper.provider;

import com.sovtech.test.graphql.server.wrapper.datafetcher.GraphQLDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Component
public class GraphQLProvider {
    private GraphQL graphQL;
    @Autowired
    private GraphQLDataFetcher dataFetcher;

    @PostConstruct
    public void init() throws IOException {
        final Resource resource = new ClassPathResource("schema.graphql");
        String sdl = null;
        try {
            sdl = new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getSwap", dataFetcher.getSwapAP()))
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getPerson", dataFetcher.getPerson()))
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getPersonByName", dataFetcher.getPersonByName()))
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getPersonByPage", dataFetcher.getPersonByPage()))
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}