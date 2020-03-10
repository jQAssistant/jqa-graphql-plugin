package org.jqassistant.contrib.plugin.graphql;

import org.jqassistant.contrib.plugin.graphql.api.model.SchemaUrlDescriptor;
import org.jqassistant.contrib.plugin.graphql.scope.GraphQLScope;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class GraphQLSchemaURLScannerPluginIT extends AbstractGraphQLSchemaScannerPluginIT<SchemaUrlDescriptor> {

    @LocalServerPort
    private int port;

    @Override
    protected SchemaUrlDescriptor scan() throws MalformedURLException {
        String url = getUrl();
        return getScanner().scan(new URL(url), url, GraphQLScope.SCHEMA);
    }

    @Test
    public void schemaURL() {
        store.beginTransaction();
        assertThat(schemaDescriptor).isNotNull();
        assertThat(schemaDescriptor.getURL()).isEqualTo(getUrl());
        store.commitTransaction();
    }

    private String getUrl() {
        return "http://localhost:" + port + "/graphql/";
    }

}


