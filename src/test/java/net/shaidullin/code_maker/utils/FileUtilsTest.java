package net.shaidullin.code_maker.utils;

import net.shaidullin.code_maker.ModuleTestData;
import net.shaidullin.code_maker.core.node.ModuleNode;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.DEFAULT)
public class FileUtilsTest {
    @BeforeClass
    public static void beforeClass() {
        ModuleTestData.initializeAutoCleanState();
    }

    @AfterClass
    public static void afterClass() {
//        ModuleTestData.deleteAutoCleanState();
    }

    @Test
    public void buildPathToMetadataTest() {
        ModuleNode securityNode = ModuleTestData.getSecurityNode();

        assertNotNull("Security node is present", securityNode);
        assertEquals("Build path to root metadata",
            ModuleTestData.AUTO_CLEAN_PATH.toAbsolutePath().toString(),
            FileUtils.buildPathToMetadata(securityNode));
    }
}
