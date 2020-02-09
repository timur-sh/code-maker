package net.shaidullin.code_maker.utils;

import net.shaidullin.code_maker.ModuleTestData;
import net.shaidullin.code_maker.core.NodeTestUtils;
import net.shaidullin.code_maker.core.metadata.MetadataSettings;
import net.shaidullin.code_maker.core.node.*;
import net.shaidullin.code_maker.integration.impl.dto.DtoIntegrationObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.DEFAULT)
public class NodeTest {
    @BeforeClass
    public static void beforeClass() {
        ModuleTestData.initializeAutoCleanState();
    }

    @AfterClass
    public static void afterClass() throws IOException {
        ModuleTestData.deleteAutoCleanState();
    }

    @Test
    public void buildModulePathToMetadataTest() {
        ModuleNode securityNode = ModuleTestData.getSecurityNode();
        String moduleMetadataPath = ModuleTestData.AUTO_CLEAN_PATH.toAbsolutePath().toString();
        // test ModuleNode
        assertNotNull("Security node is present", securityNode);
        this.testNodeInternal(securityNode, "com.security", moduleMetadataPath);


        // test ElementNode
        ElementNode dtoElementNode = ModuleTestData.autoCleanState.getElements()
            .get(securityNode).stream()
            .filter(elementNode -> new DtoIntegrationObject().getFolder().equals(elementNode.getSystemName()))
            .findFirst()
            .orElse(null);
        assertNotNull("Dto node is found", dtoElementNode);
        String dtoMetadataPath = Paths.get(moduleMetadataPath, dtoElementNode.getSystemName()).toString();
        this.testNodeInternal(dtoElementNode, "com.security.domain", dtoMetadataPath);


        // test PackageNode
        PackageNode authPackageNode = ModuleTestData.autoCleanState.getPackages()
            .get(dtoElementNode).stream()
            .filter(packageNode -> NodeTestUtils.AUTH_PACKAGE.equals(packageNode.getSystemName()))
            .findFirst()
            .orElse(null);
        assertNotNull("Auth package is not null", authPackageNode);
        String authPackagePath = Paths.get(dtoMetadataPath, authPackageNode.getSystemName()).toString();
        this.testNodeInternal(authPackageNode, "com.security.domain.auth", authPackagePath);

        // test LeafNode
        List<LeafNode> leaves = ModuleTestData.autoCleanState.getLeaves()
            .get(authPackageNode);
        assertTrue("There is not '.metadata' file", leaves.stream().noneMatch(leafNode -> MetadataSettings.METADATA_FILE_NAME.equals(leafNode.getSystemName())));
        LeafNode leafNode = leaves.stream()
            .filter(node -> NodeTestUtils.AUTHENTICATION_LEAF_NAME.equals(node.getSystemName()))
            .findFirst()
            .orElse(null);

        assertNotNull("Node is found", leafNode);
        String authenticationLeaf = Paths.get(authPackagePath, leafNode.getSystemName()).toString();
        this.testNodeInternal(leafNode, "com.security.domain.auth.Authentication",
            authenticationLeaf);


    }

    public void testNodeInternal(Node node, String expectedPackage, String expectedMetadataPath) {
        System.out.println("-- expected package=" + expectedPackage);
        System.out.println("-- expected metadataPath=" + expectedMetadataPath);

        assertEquals(expectedPackage, PackageUtils.assembleFqnClassName(node));
        assertEquals("Build path for node=" + node.getClass().getSimpleName(),
            expectedMetadataPath,
            FileUtils.buildPathToMetadata(node));

        System.out.println();
    }
}
