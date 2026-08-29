/*
 * Copyright 2026 Untold Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.untoldstudio.rimeui.core.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.*;
import java.io.IOException;

public final class LowerCaseEnumTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }

        @SuppressWarnings("unchecked")
        final Class<T> enumClass = (Class<T>) rawType;

        return new TypeAdapter<>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(((Enum<?>) value).name().toLowerCase());
                }
            }

            @Override
            public T read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String enumName = in.nextString().toUpperCase();
                try {
                    @SuppressWarnings({"unchecked", "rawtypes"})
                    T result = (T) Enum.valueOf((Class<? extends Enum>) enumClass, enumName);
                    return result;
                } catch (IllegalArgumentException e) {
                    throw new JsonParseException("Invalid enum value: " + enumName);
                }
            }
        };
    }
}