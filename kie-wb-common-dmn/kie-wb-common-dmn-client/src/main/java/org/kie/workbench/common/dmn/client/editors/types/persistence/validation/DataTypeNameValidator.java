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

package org.kie.workbench.common.dmn.client.editors.types.persistence.validation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.kie.workbench.common.dmn.client.editors.types.common.DataType;
import org.kie.workbench.common.dmn.client.editors.types.messages.DataTypeFlashMessage;
import org.kie.workbench.common.dmn.client.editors.types.persistence.DataTypeStore;

import static org.kie.workbench.common.stunner.core.util.StringUtils.isEmpty;

public class DataTypeNameValidator {

    private final Event<DataTypeFlashMessage> flashMessageEvent;

    private final NameIsBlankErrorMessage blankErrorMessage;

    private final NameIsNotUniqueErrorMessage notUniqueErrorMessage;

    private final DataTypeStore dataTypeStore;

    @Inject
    public DataTypeNameValidator(final Event<DataTypeFlashMessage> flashMessageEvent,
                                 final NameIsBlankErrorMessage blankErrorMessage,
                                 final NameIsNotUniqueErrorMessage notUniqueErrorMessage,
                                 final DataTypeStore dataTypeStore) {
        this.flashMessageEvent = flashMessageEvent;
        this.blankErrorMessage = blankErrorMessage;
        this.notUniqueErrorMessage = notUniqueErrorMessage;
        this.dataTypeStore = dataTypeStore;
    }

    public boolean isValid(final DataType dataType) {

        if (isBlank(dataType)) {
            flashMessageEvent.fire(blankErrorMessage.getFlashMessage(dataType));
            return false;
        }

        if (isNotUnique(dataType)) {
            flashMessageEvent.fire(notUniqueErrorMessage.getFlashMessage(dataType));
            return false;
        }

        return true;
    }

    public boolean isNotUnique(final DataType dataType) {

        List<DataType> siblings = siblings(dataType);
        return siblings.stream().anyMatch(sibling -> {

            final boolean isNameEquals = Objects.equals(sibling.getName(), dataType.getName());
            final boolean isOtherDataType = !Objects.equals(sibling.getUUID(), dataType.getUUID());

            return isNameEquals && isOtherDataType;
        });
    }

    boolean isBlank(final DataType dataType) {
        return isEmpty(dataType.getName());
    }

    public List<DataType> siblings(final DataType dataType) {

        final Optional<DataType> parent = Optional.ofNullable(dataTypeStore.get(dataType.getParentUUID()));

        if (parent.isPresent()) {
            return parent.get().getSubDataTypes();
        } else {
            return dataTypeStore.getTopLevelDataTypes();
        }
    }
}
