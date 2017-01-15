package org.evomaster.core.output

import org.evomaster.core.search.Solution
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Given a Solution as input, convert it to a string representation of
 * the tests that can be written to file and be compiled
 */
class TestSuiteWriter {

    companion object {

        private val indent = "    "
        private val controller = "controller"
        private val baseUrlOfSut = "baseUrlOfSut"


        fun writeTests(
                solution: Solution<*>,
                format: OutputFormat,
                outputFolder: String,
                testSuiteFileName: String,
                controllerName: String){

            val name = TestSuiteFileName(testSuiteFileName)

            val content = convertToCompilableTestCode(solution, format, name, controllerName)
            saveToDisk(content, format, outputFolder, name)
        }



        fun convertToCompilableTestCode(
                solution: Solution<*>,
                format: OutputFormat,
                testSuiteFileName: TestSuiteFileName,
                controllerName: String)
                : String {

            val buffer = StringBuilder(2048)

            header(format, testSuiteFileName, buffer)

            beforeAfterMethods(format, controllerName, buffer)

            val tests = TestSuiteOrganizer.sortTests(solution)

            for(test in tests){
                newLines(2,buffer)

                val lines = TestCaseWriter.convertToCompilableTestCode(format, test)
                lines.forEach { l -> buffer.append("    $l\n") }
            }

            footer(buffer)

            return buffer.toString()
        }


        fun saveToDisk(testFileContent: String,
                       format: OutputFormat,
                       outputFolder: String,
                       testSuiteFileName: TestSuiteFileName){

            val path = Paths.get(outputFolder, testSuiteFileName.getAsPath(format))

            Files.createDirectories(path.parent)
            Files.deleteIfExists(path)
            Files.createFile(path)

            path.toFile().appendText(testFileContent)
        }


        private fun header(format: OutputFormat, name: TestSuiteFileName, buffer: StringBuilder){

            if(name.hasPackage() && format.isJavaOrKotlin()){
                buffer.append("package "+name.getPackage())
                addSemicolon(format, buffer)
                newLine(buffer)
            }

            //TODO add generate comment with EvoMaster note and time

            newLines(2, buffer)

            if(format.isJUnit5()){
                addImport("org.junit.jupiter.api.AfterAll", buffer, format)
                addImport("org.junit.jupiter.api.BeforeAll", buffer, format)
                addImport("org.junit.jupiter.api.BeforeEach", buffer, format)
            }

            newLines(2, buffer)

            if(format.isJavaOrKotlin()){
                defineClass(format, name, buffer)
                newLine(buffer)
            }
        }


        private fun beforeAfterMethods(format: OutputFormat,
                                       controllerName: String,
                                       buffer: StringBuilder){

            //TODO check format

            newLine(buffer)
            //TODO controllerName package in the imports
            methodLine("private static $controllerName $controller = new $controllerName();", buffer)
            methodLine("private static String $baseUrlOfSut;", buffer)
            newLines(2, buffer)


            methodLine("@BeforeAll", buffer)
            methodLine("public static void initClass() {", buffer)
            blockLine("boolean started = $controller.startSUT();", buffer)
            blockLine("assertTrue(started);", buffer)
            newLine(buffer)
            blockLine("SutInfoDto dto = remoteController.getSutInfo();",buffer)
            blockLine("assertNotNull(dto);", buffer)
            newLine(buffer)
            blockLine("baseUrlOfSut = dto.baseUrlOfSUT;",buffer)
            blockLine("assertNotNull(baseUrlOfSut);", buffer)
            methodLine("}", buffer)
            newLines(2, buffer)


            methodLine("@AfterAll", buffer)
            methodLine("public static void tearDown() {", buffer)
            blockLine("boolean stopped = $controller.stopSUT();", buffer)
            blockLine("assertTrue(stopped);", buffer)
            methodLine("}", buffer)
            newLines(2, buffer)


            methodLine("@BeforeEach", buffer)
            methodLine("public void initTest() {", buffer)
            blockLine("boolean reset = $controller.resetSUT();", buffer)
            blockLine("assertTrue(reset);", buffer)
            methodLine("}", buffer)
            newLines(2, buffer)
        }


        private fun methodLine(line: String, buffer: StringBuilder){
            buffer.append(indent + line)
            newLine(buffer)
        }

        private fun blockLine(line: String, buffer: StringBuilder){
            methodLine(indent + line, buffer)
        }

        private fun footer(buffer: StringBuilder){

            newLines(2, buffer);
            buffer.append("}")
        }

        private fun defineClass(format: OutputFormat, name: TestSuiteFileName, buffer: StringBuilder){

            when{
                format.isJava() -> buffer.append("public ")
                format.isKotlin() -> buffer.append("internal ")
            }

            buffer.append("class ${name.getClassName()} {")
        }

        private fun addImport(klass: String, buffer: StringBuilder, format: OutputFormat){

            buffer.append("import $klass")
            addSemicolon(format, buffer)
            newLine(buffer)
        }

        private fun newLines(n: Int, buffer: StringBuilder){
            if(n <= 0){
                throw IllegalArgumentException("Invalid n=$n")
            }

            (1..n).forEach { newLine(buffer) }
        }

        private fun newLine(buffer: StringBuilder){
            buffer.append("\n")
        }

        private fun addSemicolon(format: OutputFormat, buffer: StringBuilder) {
            if(format.isJava()){
                buffer.append(";")
            }
        }
    }
}