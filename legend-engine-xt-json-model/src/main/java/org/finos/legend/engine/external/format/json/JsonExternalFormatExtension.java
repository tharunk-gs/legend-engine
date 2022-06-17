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

package org.finos.legend.engine.external.format.json;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.list.ImmutableList;
import org.finos.legend.engine.external.format.json.compile.JSONSchemaMetamodelParserVisitor;
import org.finos.legend.engine.external.format.json.compile.JsonSchemaCompiler;
import org.finos.legend.engine.external.format.json.fromModel.JSONSchemaComposer;
import org.finos.legend.engine.external.format.json.fromModel.ModelToJsonSchemaConfiguration;
import org.finos.legend.engine.external.format.json.toModel.JsonSchemaToModelConfiguration;
import org.finos.legend.engine.external.shared.format.model.ExternalFormatExtension;
import org.finos.legend.engine.external.shared.format.model.ExternalSchemaCompileContext;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.CompileContext;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.protocol.pure.PureClientVersions;
import org.finos.legend.pure.generated.Root_meta_external_format_json_binding_fromPure_ModelToJsonSchemaConfiguration;
import org.finos.legend.pure.generated.Root_meta_external_format_json_binding_fromPure_ModelToJsonSchemaConfiguration_Impl;
import org.finos.legend.pure.generated.Root_meta_external_format_json_binding_toPure_JsonSchemaToModelConfiguration;
import org.finos.legend.pure.generated.Root_meta_external_format_json_binding_toPure_JsonSchemaToModelConfiguration_Impl;
import org.finos.legend.pure.generated.Root_meta_external_format_json_metamodel_JSONSchema;
import org.finos.legend.pure.generated.Root_meta_external_shared_format_binding_Binding;
import org.finos.legend.pure.generated.Root_meta_external_shared_format_binding_validation_BindingDetail;
import org.finos.legend.pure.generated.Root_meta_external_shared_format_metamodel_SchemaSet;
import org.finos.legend.pure.generated.Root_meta_pure_generation_metamodel_GenerationParameter;
import org.finos.legend.pure.generated.core_external_format_json_binding_jsonSchemaToPure;
import org.finos.legend.pure.generated.core_external_format_json_binding_pureToJsonSchema;

import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.List;

public class JsonExternalFormatExtension implements ExternalFormatExtension<Root_meta_external_format_json_metamodel_JSONSchema, JsonSchemaToModelConfiguration, ModelToJsonSchemaConfiguration>
{
    public static final String TYPE = "JSON";
    private static final boolean IN_DEBUG = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains(":jdwp");

    @Override
    public boolean supportsModelGeneration()
    {
        return true;
    }

    @Override
    public RichIterable<? extends Root_meta_pure_generation_metamodel_GenerationParameter> getModelGenerationProperties(PureModel pureModel)
    {
        return core_external_format_json_binding_jsonSchemaToPure.Root_meta_external_format_json_binding_toPure_describeConfiguration__GenerationParameter_MANY_(pureModel.getExecutionSupport());
    }

    @Override
    public boolean supportsSchemaGeneration()
    {
        return true;
    }

    @Override
    public RichIterable<? extends Root_meta_pure_generation_metamodel_GenerationParameter> getSchemaGenerationProperties(PureModel pureModel)
    {
        return core_external_format_json_binding_pureToJsonSchema.Root_meta_external_format_json_binding_fromPure_describeConfiguration__GenerationParameter_MANY_(pureModel.getExecutionSupport());
    }

    @Override
    public String getFormat()
    {
        return TYPE;
    }

    @Override
    public List<String> getContentTypes()
    {
        return Collections.singletonList(JsonExternalFormatPureExtension.CONTENT_TYPE);
    }

    @Override
    public Root_meta_external_format_json_metamodel_JSONSchema compileSchema(ExternalSchemaCompileContext context)
    {
        return new JsonSchemaCompiler(context).compile();
    }

    @Override
    public Root_meta_external_shared_format_binding_validation_BindingDetail bindDetails(Root_meta_external_shared_format_binding_Binding binding, CompileContext context)
    {
        // TODO: current binding validation flow fails when the input schema is just a collection
        return null;
    }

    @Override
    public String metamodelToText(Root_meta_external_format_json_metamodel_JSONSchema schemaDetail, PureModel pureModel)
    {
        return new JSONSchemaComposer(new JSONSchemaMetamodelParserVisitor().visit(schemaDetail)).compose();
    }

    @Override
    public Root_meta_external_shared_format_binding_Binding generateModel(Root_meta_external_shared_format_metamodel_SchemaSet schemaSet, JsonSchemaToModelConfiguration config, PureModel pureModel)
    {
        Root_meta_external_format_json_binding_toPure_JsonSchemaToModelConfiguration configuration = new Root_meta_external_format_json_binding_toPure_JsonSchemaToModelConfiguration_Impl("")
                ._sourceSchemaId(config.sourceSchemaId)
                ._targetBinding(config.targetBinding)
                ._targetPackage(config.targetPackage);
        return IN_DEBUG
                ? core_external_format_json_binding_jsonSchemaToPure.Root_meta_external_format_json_binding_toPure_jsonSchemaToPureWithDebug_SchemaSet_1__JsonSchemaToModelConfiguration_1__Binding_1_(schemaSet, configuration, pureModel.getExecutionSupport())
                : core_external_format_json_binding_jsonSchemaToPure.Root_meta_external_format_json_binding_toPure_jsonSchemaToPure_SchemaSet_1__JsonSchemaToModelConfiguration_1__Binding_1_(schemaSet, configuration, pureModel.getExecutionSupport());
    }

    @Override
    public Root_meta_external_shared_format_binding_Binding generateSchema(ModelToJsonSchemaConfiguration config, PureModel pureModel)
    {
        Root_meta_external_format_json_binding_fromPure_ModelToJsonSchemaConfiguration configuration = new Root_meta_external_format_json_binding_fromPure_ModelToJsonSchemaConfiguration_Impl("")
                ._targetBinding(config.targetBinding)
                ._targetSchemaSet(config.targetSchemaSet)
                ._specificationUrl(config.specificationUrl != null ? config.specificationUrl : JSONSchemaComposer.DEFAULT_COMPOSER)
                ._useConstraints(config.useConstraints != null ? config.useConstraints : true)
                ._includeAllRelatedTypes(config.includeAllRelatedTypes != null ? config.includeAllRelatedTypes : true)
                ._generateAnyOfSubType(config.generateAnyOfSubType != null ? config.generateAnyOfSubType : true)
                ._generateConstraintFunctionSchemas(config.useConstraints != null ? config.useConstraints : false)
                ._createSchemaCollection(config.createSchemaCollection != null ? config.createSchemaCollection : false)
                ._generateMilestoneProperties(config.generateMilestoneProperties != null ? config.generateMilestoneProperties : false)
                ._rootLevel(true)
                ._useDefinitions(config.includeAllRelatedTypes != null ? config.includeAllRelatedTypes : true);

        config.sourceModel.forEach(pe -> configuration._sourceModelAdd(pureModel.getPackageableElement(pe)));

        return IN_DEBUG
                ? core_external_format_json_binding_pureToJsonSchema.Root_meta_external_format_json_binding_fromPure_pureToJsonSchemaWithDebug_ModelToJsonSchemaConfiguration_1__Binding_1_(configuration, pureModel.getExecutionSupport())
                : core_external_format_json_binding_pureToJsonSchema.Root_meta_external_format_json_binding_fromPure_pureToJsonSchema_ModelToJsonSchemaConfiguration_1__Binding_1_(configuration, pureModel.getExecutionSupport());
    }

    @Override
    public List<String> getRegisterablePackageableElementNames()
    {
        ImmutableList<String> versions = PureClientVersions.versionsSince("v1_23_0");
        return versions.collect(v -> "meta::protocols::pure::" + v + "::external::format::json::serializerExtension_String_1__SerializerExtension_1_").toList();
    }
}
