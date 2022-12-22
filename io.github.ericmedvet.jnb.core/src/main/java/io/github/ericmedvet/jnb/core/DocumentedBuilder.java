/*
 * Copyright 2022 eric
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

package io.github.ericmedvet.jnb.core;

import java.util.List;

/**
 * @author "Eric Medvet" on 2022/08/12 for 2dmrsim
 */
public interface DocumentedBuilder<T> extends Builder<T> {
  enum Type {
    INT("i"),
    DOUBLE("d"),
    STRING("s"),
    BOOLEAN("b"),
    ENUM("e"),
    NAMED_PARAM_MAP("npm"),
    INTS("i[]"),
    DOUBLES("d[]"),
    STRINGS("s[]"),
    BOOLEANS("b[]"),
    ENUMS("e[]"),
    NAMED_PARAM_MAPS("npm[]");
    private final String rendered;

    Type(String rendered) {
      this.rendered = rendered;
    }

    public String rendered() {
      return rendered;
    }
  }

  record ParamInfo(
      Type type, Class<?> enumClass, String name, Object defaultValue, Param.Injection injection,
      java.lang.reflect.Type javaType
  ) {
    @Override
    public String toString() {
      return String.format(
          "%s = %s%s",
          injection.equals(Param.Injection.NONE) ? name : injection.toString().toLowerCase(),
          type.rendered(),
          defaultValue == null ? "" : ("{" + defaultValue + "}")
      );
    }
  }

  java.lang.reflect.Type builtType();

  String name();

  List<ParamInfo> params();

}
