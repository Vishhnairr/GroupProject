import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.lang.reflect.Modifier;

/**
 * A framework to run public test cases for phase 1, Team Project.
 *
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author J Morris Purdue CS
 *
 * @version March 31, 2024
 */

public class RunLocalTest {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    public static class TestCase {

        private static final String INFILE = "input.txt";

        @Test(timeout = 1000)
        public void BadDataExceptionDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = BadDataException.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `BadDataException` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `BadDataException` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `BadDataException` extends `Exception`!",
                    Exception.class, superclass);
            Assert.assertEquals("Ensure that `BadDataException` implements no interfaces!",
                    0, superinterfaces.length);
        }

        @Test(timeout = 1000)
        public void SCPClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = SCP.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `SCP` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `SCP` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `SCP` extends `Object`!",
                    Object.class, superclass);
            Assert.assertEquals("Ensure that `SCP` implements no interfaces!",
                    0, superinterfaces.length);
        }

        @Test(timeout = 1000)
        public void ResearcherClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = Researcher.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `Researcher` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `Researcher` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `Researcher` extends `Object`!",
                    Object.class, superclass);
            Assert.assertEquals("Ensure that `Researcher` implements no interfaces!",
                    0, superinterfaces.length);
        }

        @Test(timeout = 1000)
        public void TheSCPFoundationClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = TheSCPFoundation.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `TheSCPFoundation` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `TheSCPFoundation` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `TheSCPFoundation` extends `Object`!",
                    Object.class, superclass);
            Assert.assertEquals("Ensure that `TheSCPFoundation` implements no interfaces!",
                    0, superinterfaces.length);
        }

        @Test(timeout = 1000)
        public void FoundationDatabaseClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = FoundationDatabase.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `FoundationDatabase` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `FoundationDatabase` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `FoundationDatabase` extends `Object`!",
                    Object.class, superclass);
            Assert.assertEquals("Ensure that `FoundationDatabase` implements no interfaces!",
                    0, superinterfaces.length);
        }

        @Test(timeout = 1000)
        public void runTestFoundationDatabaseOut() {
            String scpFileString = "scpIn.txt";
            String researcherFileString = "researcherIn.txt";
            String dbOutFileString = "dataOut.txt";
            String mainOutFileString = "mainOut.txt";

            String input = String.format("%s %s %s %s\n", scpFileString, researcherFileString,
                    dbOutFileString, mainOutFileString) +
                    "1\n" +
                    "2\n" +
                    "3\n" +
                    "6\n" +
                    "105\n" +
                    "Marcus Castañeda,791370344,4,B,true\n" +
                    "7";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(INFILE))) {
                writer.write(input);
                writer.flush();
            } catch (Exception e) {
                Assert.assertTrue("An Exception was encountered when rewriting input.txt", false);
            }

            TheSCPFoundation.main(new String[0]);

            String expectedDatabaseOutput = "SCP-105,\"Iris\",SAFE,3,false,Conner Hiley,275363218,3,D,true," +
                    "Marcus Castañeda,791370344,4,B,true\n" +
                    "SCP-133,Instant Hole,SAFE,4,false,Hector Estacio,42903901,4,A,true\n" +
                    "SCP-141,Codex Damnatio,SAFE,2,false,Genaoveva Victoria,703623549,2,A,true\n" +
                    "SCP-176,Observable Time Loop,EUCLID,3,false,Seth Enbody,364440049,3,A,true\n" +
                    "SCP-186,To End All Wars,EUCLID,1,true,Solomon Basilio,14231368,1,D,true\n" +
                    "SCP-265,Black Volga,EUCLID,5,true,VACANT\n" +
                    "SCP-327,Not Found,EUCLID,1,true,Kimberly Madera,586995719,1,D,true\n" +
                    "SCP-340,Viral Rebreather Membrane,SAFE,1,false,Lenel Malik,7569754,1,D,true\n" +
                    "SCP-489,1-555-BUG-BASH,EUCLID,2,true,Ida Finn,909779603,2,C,true\n" +
                    "SCP-607,Dorian the Grey Cat,EUCLID,1,false,Celeste Sanjuan,17708213,1,A,true\n" +
                    "SCP-698,Judgmental Turtle,EUCLID,5,false,Binh Esteban,624876107,5,A,true\n" +
                    "SCP-768,Long-Range Alarm Clock,SAFE,3,true,Jesper Carr,192430489,5,B,true\n" +
                    "SCP-841,Reverse Mirror Voodoo Doll Stick Puppet,SAFE,2,false,Amor Sandrian,299997685,2,A,true\n" +
                    "SCP-936,Fruit of Man,EUCLID,5,true,VACANT\n" +
                    "SCP-969,Brand Mosquito Repellent,EUCLID,2,true,Viven Kartman,780159039,2,C,true\n";

            String actualDatabaseOutput = "";

            try (BufferedReader reader = new BufferedReader(new FileReader(dbOutFileString))) {
                String in = "";
                while ((in = reader.readLine()) != null) {
                    actualDatabaseOutput += in + "\n";
                }
            } catch (IOException a) {
                Assert.assertTrue("An IO exception was encountered while reading dataOut.txt", false);
            } catch (Exception e) {
                Assert.assertTrue("An unknown exception was encountered while reading dataOut.txt", false);
            }

            Assert.assertEquals("Make sure your FoundationDatabase is writing the outputfile correctly",
                    expectedDatabaseOutput.trim(), actualDatabaseOutput.trim());

        }

        @Test(timeout = 1000)
        public void runTestTheSCPFoundationOut() {
            String scpFileString = "scpIn.txt";
            String researcherFileString = "researcherIn.txt";
            String dbOutFileString = "dataOut.txt";
            String mainOutFileString = "mainOut.txt";

            String input = String.format("%s %s %s %s\n", scpFileString, researcherFileString,
                    dbOutFileString, mainOutFileString) +
                    "1\n" +
                    "2\n" +
                    "3\n" +
                    "6\n" +
                    "105\n" +
                    "Marcus Castañeda,791370344,4,B,true\n" +
                    "7";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(INFILE))) {
                writer.write(input);
                writer.flush();
            } catch (Exception e) {
                Assert.assertTrue("An Exception was encountered when rewriting input.txt", false);
            }

            TheSCPFoundation.main(new String[0]);

            String expectedDatabaseOutput = "Foundation Database Started\n" +
                    "1 Success\n" +
                    "2 Success\n" +
                    "3 Failure\n" +
                    "6 Success\n" +
                    "7 Success\n";

            String actualTheSCPFoundationOutput = "";

            try (BufferedReader reader = new BufferedReader(new FileReader(mainOutFileString))) {
                String in = "";
                while ((in = reader.readLine()) != null) {
                    actualTheSCPFoundationOutput += in + "\n";
                }
            } catch (IOException a) {
                Assert.assertTrue("An IO exception was encountered while reading dataOut.txt", false);
            } catch (Exception e) {
                Assert.assertTrue("An unknown exception was encountered while reading dataOut.txt", false);
            }

            Assert.assertEquals("Make sure your FoundationDatabase is writing the outputfile correctly",
                    expectedDatabaseOutput.trim(), actualTheSCPFoundationOutput.trim());

        }

    }

}

