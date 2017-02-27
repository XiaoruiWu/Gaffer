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

package uk.gov.gchq.gaffer.operation.export.resultcache.handler;

import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser;
import uk.gov.gchq.gaffer.operation.export.resultcache.GafferResultCacheExporter;
import uk.gov.gchq.gaffer.operation.export.resultcache.handler.util.GafferResultCacheUtil;
import uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache;
import uk.gov.gchq.gaffer.store.Context;
import uk.gov.gchq.gaffer.store.Store;
import uk.gov.gchq.gaffer.store.StoreProperties;
import uk.gov.gchq.gaffer.store.operation.handler.export.ExportHandler;
import java.nio.file.Paths;

public class ExportToGafferResultCacheHandler extends ExportHandler<ExportToGafferResultCache, GafferResultCacheExporter> {
    /**
     * Time to live in milliseconds.
     */
    private Long timeToLive = GafferResultCacheUtil.DEFAULT_TIME_TO_LIVE;

    private String visibility;

    private StoreProperties storeProperties;

    private JSONSerialiser jsonSerialiser = new JSONSerialiser();

    @Override
    protected Class<GafferResultCacheExporter> getExporterClass() {
        return GafferResultCacheExporter.class;
    }

    @Override
    protected GafferResultCacheExporter createExporter(final ExportToGafferResultCache export, final Context context, final Store store) {
        return new GafferResultCacheExporter(
                context.getUser(), context.getJobId(), createGraph(store),
                jsonSerialiser, visibility, export.getOpAuths());
    }

    protected Graph createGraph(final Store store) {
        return GafferResultCacheUtil.createGraph(storeProperties, timeToLive);
    }

    public Long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(final Long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }

    public StoreProperties getStoreProperties() {
        return storeProperties;
    }

    public void setStoreProperties(final StoreProperties storeProperties) {
        this.storeProperties = storeProperties;
    }

    public void setStorePropertiesPath(final String path) {
        setStoreProperties(StoreProperties.loadStoreProperties(Paths.get(path)));
    }

    public JSONSerialiser getJsonSerialiser() {
        return jsonSerialiser;
    }

    public void setJsonSerialiser(final JSONSerialiser jsonSerialiser) {
        if (null == jsonSerialiser) {
            this.jsonSerialiser = new JSONSerialiser();
        } else {
            this.jsonSerialiser = jsonSerialiser;
        }
    }
}