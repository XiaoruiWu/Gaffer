/*
 * Copyright 2016 Crown Copyright
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
package uk.gov.gchq.gaffer.function.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Sets;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import uk.gov.gchq.gaffer.function.SimpleFilterFunction;
import uk.gov.gchq.gaffer.function.annotation.Inputs;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * An <code>IsIn</code> is a {@link SimpleFilterFunction} that checks that the input object is
 * in a set of allowed values.
 */
@Inputs(Object.class)
public class IsIn extends SimpleFilterFunction<Object> {
    private Set<Object> allowedValues;

    public IsIn() {
        // Required for serialisation
    }

    public IsIn(final Collection<Object> controlData) {
        this.allowedValues = new HashSet<>(controlData);
    }

    public IsIn(final Object... controlData) {
        this.allowedValues = Sets.newHashSet(controlData);
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    @JsonProperty("values")
    public Object[] getAllowedValuesArray() {
        return null != allowedValues ? allowedValues.toArray() : new Object[0];
    }

    @JsonProperty("values")
    public void setAllowedValues(final Object[] allowedValuesArray) {
        if (null != allowedValuesArray) {
            allowedValues = new HashSet<>(Arrays.asList(allowedValuesArray));
        } else {
            allowedValues = new HashSet<>(0);
        }
    }

    @JsonIgnore
    public Set<Object> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(final Set<Object> allowedValues) {
        this.allowedValues = allowedValues;
    }

    public IsIn statelessClone() {
        return new IsIn(allowedValues);
    }

    @Override
    public boolean isValid(final Object input) {
        return allowedValues.contains(input);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final IsIn isIn = (IsIn) o;

        return new EqualsBuilder()
                .append(inputs, isIn.inputs)
                .append(allowedValues, isIn.allowedValues)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(inputs)
                .append(allowedValues)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("inputs", inputs)
                .append("allowedValues", allowedValues)
                .toString();
    }
}
