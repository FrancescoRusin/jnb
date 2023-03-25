package io.github.ericmedvet.jnb;

import io.github.ericmedvet.jnb.core.*;

import java.util.List;

public class Main {

  private final static String S = """
      office(
              head = person(name = "Mario Rossi"; pet = pet(booleans = [true]));
              staff = + [
                person(name = Alice; age = 33; nicknames = [Puce; "The Cice"]; gender = f);
                person(name = Bob);
                person(name = Charlie; age = 38)
              ] + [person(name = Dane; age = 28)];
              roomNumbers = [202:1:205]
            )
      """;

  public enum DayOfWeek {MON, TUE, WED, THU, FRI, SAT, SUN}

  public enum Gender {M, F, OTHER}

  public record Office(
      @Param("roomNumbers") List<Integer> roomNumbers,
      @Param("head") Person head,
      @Param("staff") List<Person> staff,
      @Param(value = "spareStaff", dNPMs = {"person(name = Gigi)"}) List<Person> spareStaff
  ) {}

  public record Person(
      @Param(value = "", injection = Param.Injection.INDEX) int index,
      @Param("name") String name,
      @Param(value = "gender", dS = "m") Gender gender,
      @Param(value = "age", dI = 44) int age,
      @Param(value = "nice", dB = true) boolean nice,
      @Param("nicknames") List<String> nicknames,
      @Param(value = "pet", dNPM = "pet(name=Fido)") Pet pet,
      @Param(value = "preferredDays", dSs = {"mon", "fri"}) List<DayOfWeek> preferredDays,
      @Param(value = "", injection = Param.Injection.MAP_WITH_DEFAULTS) ParamMap map
  ) {}

  public record Pet(
      @Param("name") String name,
      @Param(value = "kind", dS = "dog") String kind,
      @Param(value = "legs", dIs = {4}) List<Integer> legs,
      @Param(value = "booleans", dBs = {false, false}) List<Boolean> booleans
  ) {}

  public static void main(String[] args) {
    NamedBuilder<?> namedBuilder = NamedBuilder.empty()
        .and(NamedBuilder.fromClass(Office.class))
        .and(NamedBuilder.fromClass(Person.class))
        .and(NamedBuilder.fromClass(Pet.class));
    Office office = (Office) namedBuilder.build(S);
    System.out.println(office);
    System.out.println(MapNamedParamMap.prettyToString(StringParser.parse(S)));
    System.out.println(MapNamedParamMap.prettyToString(namedBuilder.fillWithDefaults(StringParser.parse(S))));
  }
}
