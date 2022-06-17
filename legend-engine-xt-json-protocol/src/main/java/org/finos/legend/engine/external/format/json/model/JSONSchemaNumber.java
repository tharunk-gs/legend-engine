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

package org.finos.legend.engine.external.format.json.model;

import org.finos.legend.engine.external.format.json.visitor.JSONSchemaVisitor;

public class JSONSchemaNumber extends JSONSchema
{
    public final Number minimum;
    public final Number maximum;
    public final Number exclusiveMinimum;
    public final Number exclusiveMaximum;
    public final Number multipleOf;
    public final String format;

    protected JSONSchemaNumber(JSONSchemaNumber.JSONSchemaNumberBuilder<?> b)
    {
        super(b);
        this.minimum = b.minimum;
        this.maximum = b.maximum;
        this.exclusiveMinimum = b.exclusiveMinimum;
        this.exclusiveMaximum = b.exclusiveMaximum;
        this.multipleOf = b.multipleOf;
        this.format = b.format;
    }

    public static class JSONSchemaNumberBuilder<B extends JSONSchemaNumber.JSONSchemaNumberBuilder<B>> extends JSONSchema.JSONSchemaBuilder<B>
    {
        private Number minimum;
        private Number maximum;
        private Number exclusiveMinimum;
        private Number exclusiveMaximum;
        private Number multipleOf;
        private String format;

        public JSONSchemaNumber build()
        {
            return new JSONSchemaNumber(this);
        }

        public B minimum(Number minimum)
        {
            this.minimum = minimum;
            return this.self();
        }

        public B maximum(Number maximum)
        {
            this.maximum = maximum;
            return this.self();
        }

        public B exclusiveMinimum(Number exclusiveMinimum)
        {
            this.exclusiveMinimum = exclusiveMinimum;
            return this.self();
        }

        public B exclusiveMaximum(Number exclusiveMaximum)
        {
            this.exclusiveMaximum = exclusiveMaximum;
            return this.self();
        }

        public B multipleOf(Number multipleOf)
        {
            this.multipleOf = multipleOf;
            return this.self();
        }

        public B format(String format)
        {
            this.format = format;
            return this.self();
        }

    }

    public <T> T accept(JSONSchemaVisitor<T> visitor)
    {
        return visitor.visit(this);
    }

    public static JSONSchemaNumber.JSONSchemaNumberBuilder<?> builder()
    {
        return new JSONSchemaNumber.JSONSchemaNumberBuilder<>();
    }
}
