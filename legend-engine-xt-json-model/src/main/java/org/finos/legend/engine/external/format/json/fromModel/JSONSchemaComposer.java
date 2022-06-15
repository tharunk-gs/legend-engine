//  Copyright 2022 Goldman Sachs
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.

package org.finos.legend.engine.external.format.json.fromModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.finos.legend.engine.external.format.json.JSONSchemaSpecificationExtension;
import org.finos.legend.engine.external.format.json.JSONSchemaSpecificationExtensionLoader;
import org.finos.legend.engine.external.format.json.model.JSONSchema;
import org.finos.legend.engine.external.format.json.specifications.draftv7.DraftV7SchemaSpecificationExtension;
import org.finos.legend.engine.shared.core.operational.errorManagement.EngineException;

import java.util.Map;

import static org.finos.legend.engine.protocol.pure.v1.PureProtocolObjectMapperFactory.getNewObjectMapper;

public class JSONSchemaComposer
{
    private static final String DEFAULT_COMPOSER = DraftV7SchemaSpecificationExtension.SCHEMA_URI;
    final Map<String, JSONSchemaSpecificationExtension> extensions = JSONSchemaSpecificationExtensionLoader.extensions();
    private final JSONSchemaSpecificationExtension inferredSchema;
    private final JSONSchema protocolModel;


    public JSONSchemaComposer(JSONSchema protocolModel)
    {
        JSONSchemaSpecificationExtension userSchemaSpec = extensions.get(DEFAULT_COMPOSER);
        this.protocolModel = protocolModel;

        if (this.protocolModel.schema != null)
        {
            String schema = this.protocolModel.schema;
            userSchemaSpec = extensions.get(schema);
            if (userSchemaSpec == null)
            {
                throw new EngineException("Cannot find JSON Schema specification for " + schema);
            }
        }

        inferredSchema = userSchemaSpec;
    }

    private static JsonNode getJsonNode(String jsonSchema)
    {
        try
        {
            return getNewObjectMapper().readTree(jsonSchema);
        }
        catch (JsonProcessingException e)
        {
            throw new EngineException(e.getMessage());
        }
    }

    public String compose()
    {
        return inferredSchema.compose(protocolModel);
    }

}
