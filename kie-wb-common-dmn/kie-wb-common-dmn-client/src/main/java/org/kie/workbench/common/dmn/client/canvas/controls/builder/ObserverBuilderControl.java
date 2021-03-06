/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.dmn.client.canvas.controls.builder;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.kie.workbench.common.dmn.api.qualifiers.DMNEditor;
import org.kie.workbench.common.stunner.core.client.api.ClientDefinitionManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.impl.Observer;
import org.kie.workbench.common.stunner.core.client.canvas.event.selection.CanvasSelectionEvent;
import org.kie.workbench.common.stunner.core.client.command.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.i18n.ClientTranslationMessages;
import org.kie.workbench.common.stunner.core.client.service.ClientFactoryService;
import org.kie.workbench.common.stunner.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.kie.workbench.common.stunner.core.rule.RuleManager;

@DMNEditor
@Dependent
@Observer
public class ObserverBuilderControl extends org.kie.workbench.common.stunner.core.client.canvas.controls.builder.impl.ObserverBuilderControl {

    @Inject
    public ObserverBuilderControl(final ClientDefinitionManager clientDefinitionManager,
                                  final ClientFactoryService clientFactoryServices,
                                  final RuleManager ruleManager,
                                  final @DMNEditor CanvasCommandFactory<AbstractCanvasHandler> canvasCommandFactory,
                                  final ClientTranslationMessages translationMessages,
                                  final GraphBoundsIndexer graphBoundsIndexer,
                                  final Event<CanvasSelectionEvent> canvasSelectionEvent) {
        super(clientDefinitionManager,
              clientFactoryServices,
              ruleManager,
              canvasCommandFactory,
              translationMessages,
              graphBoundsIndexer,
              canvasSelectionEvent);
    }
}
