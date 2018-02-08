package com.codecool.idontspeakjava.queststore.models;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class UserTest {

    @Test
    public void constructorShouldInitiateClassProperly() {
        User user = new User("Jan", "Kowalski", "98nc2y39xm30", "jan.kowalski@onet.pl", Permissions.Student);

        assertArrayEquals(new String[]{"Jan",
                        "Kowalski",
                        "98nc2y39xm30",
                        "jan.kowalski@onet.pl",
                        "Student",
                        "Jan Kowalski"},
                new String[]{user.getFirstName(),
                        user.getLastName(),
                        user.getPasswordHash(),
                        user.getEmail(),
                        user.getPermission().name(),
                        user.getFullName()});
    }

    @Test
    public void settersAndGettersShouldSetAndReturnCorrectValue() {

        User user = new User("Jan", "Kowalski", "98nc2y39xm30", "jan.kowalski@onet.pl", Permissions.Student);

        user.setFirstName("Zbigniew");
        user.setLastName("Stonoga");
        user.setPasswordHash("c23c123c123");
        user.setEmail("zbigniew.stonoga@karakan.com");
        user.setId(1234);
        user.setPermission(Permissions.Mentor);

        assertArrayEquals(new String[]{"Zbigniew",
                        "Stonoga",
                        "c23c123c123",
                        "zbigniew.stonoga@karakan.com",
                        "Mentor",
                        "1234",
                        "Zbigniew Stonoga"},
                new String[]{user.getFirstName(),
                        user.getLastName(),
                        user.getPasswordHash(),
                        user.getEmail(),
                        user.getPermission().name(),
                        String.valueOf(user.getId()),
                        user.getFullName()});
    }

    @Test
    public void builderShouldInitiateNewObjectWithCorrectFields() {
        User user = new User.Builder()
                .setFirstName("Józef")
                .setLastName("Piłsudski")
                .setEmail("jozef.pilsudski@polska.com")
                .setId(321231)
                .setPasswordHash("dkosdakosdako")
                .setPermission(Permissions.Root)
                .build();


        assertArrayEquals(new String[]{"Józef",
                        "Piłsudski",
                        "dkosdakosdako",
                        "jozef.pilsudski@polska.com",
                        "Root",
                        "321231",
                        "Józef Piłsudski"},
                new String[]{user.getFirstName(),
                        user.getLastName(),
                        user.getPasswordHash(),
                        user.getEmail(),
                        user.getPermission().name(),
                        String.valueOf(user.getId()),
                        user.getFullName()});
    }


}

