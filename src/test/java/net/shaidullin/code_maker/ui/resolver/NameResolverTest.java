package net.shaidullin.code_maker.ui.resolver;

import net.shaidullin.code_maker.ModuleTestData;
import net.shaidullin.code_maker.core.NodeTestUtils;
import net.shaidullin.code_maker.core.Pair;
import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.type.Type;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


@FixMethodOrder(MethodSorters.DEFAULT)
public class NameResolverTest {
    private final NameResolverManager resolverManager = NameResolverManager.getInstance();

    private final static Pair<UUID, String> typeParameter = new Pair<>(UUID.fromString("00000000-0000-0000-0000-000000001"), "GenTypeParameter");
    private final static Pair<UUID, String> typeArgument = new Pair<>(UUID.fromString("00000000-0000-0000-0000-000000002"), "GenArgumentParameter");
    private final static Pair<UUID, String> usualDto = new Pair<>(UUID.fromString("00000000-0000-0000-0000-000000003"), "UsualDto");

    @BeforeClass
    public static void beforeClass() {
        ModuleTestData.initializeAutoCleanState();

        PackageNode packageNode = ModuleTestData.autoCleanState.getPackages().values().stream()
            .flatMap(Collection::stream)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("There is not one package"));

        NodeTestUtils.addLeaf(usualDto.getSecond(), usualDto.getFirst(), packageNode);
        NodeTestUtils.addLeaf(typeParameter.getSecond(), typeParameter.getFirst(), packageNode, true, "T", null);
        NodeTestUtils.addLeaf(typeArgument.getSecond(), typeArgument.getFirst(), packageNode, false, null, usualDto.getFirst(), typeParameter.getFirst());

        ModuleTestData.autoCleanState.refreshState();
    }

    @AfterClass
    public static void afterClass() throws IOException {
        ModuleTestData.deleteAutoCleanState();
    }

    @Test
    public void resolveMetadataTypeTest() {
        Type type = ModuleTestData.autoCleanState.getTypeManager()
            .getTypeByUID(usualDto.getFirst());

        assertEquals(usualDto.getSecond(), type.toString());
        assertEquals(usualDto.getSecond(), resolverManager.resolveJava(type, false));
        assertEquals(usualDto.getSecond(), resolverManager.resolveJava(type, true));

        String typeParamName = "GenTypeParameter<T>";
        type = ModuleTestData.autoCleanState.getTypeManager()
            .getTypeByUID(typeParameter.getFirst());
        assertEquals(typeParamName, type.toString());
        assertEquals(typeParamName, resolverManager.resolveJava(type, false));
        assertEquals(typeParamName, resolverManager.resolveJava(type, true));

        type = ModuleTestData.autoCleanState.getTypeManager()
            .getTypeByUID(typeArgument.getFirst());
        assertEquals(typeArgument.getSecond(), type.toString());
    }

    @Test
    public void resolveDefaultTypeTest() {
        Type type = getTypeByName("Integer");

        System.out.println(type);
        assertEquals("int", type.toString());
        assertEquals("Integer", resolverManager.resolveJava(type, false));
        assertEquals("int", resolverManager.resolveJava(type, true));
    }

    @Test
    public void resolveDtoMetadataTest() {
        LeafNode leafNode = ModuleTestData.autoCleanState.getLeafByUID(usualDto.getFirst());
        assertEquals(usualDto.getSecond(), leafNode.toString());
        assertEquals(usualDto.getSecond(), resolverManager.resolveJava(leafNode.getMetadata(), false));
        assertEquals(usualDto.getSecond(), resolverManager.resolveJava(leafNode.getMetadata(), true));

        String typeParamName = "GenTypeParameter<T>";
        leafNode = ModuleTestData.autoCleanState.getLeafByUID(typeParameter.getFirst());
        assertEquals(typeParamName, leafNode.toString());
        assertEquals(typeParamName, resolverManager.resolveJava(leafNode.getMetadata(), false));
        assertEquals(typeParamName, resolverManager.resolveJava(leafNode.getMetadata(), true));

        leafNode = ModuleTestData.autoCleanState.getLeafByUID(typeArgument.getFirst());
        assertEquals(typeArgument.getSecond(), leafNode.toString());
        assertEquals(typeArgument.getSecond(), resolverManager.resolveJava(leafNode.getMetadata(), false));
        assertEquals(typeArgument.getSecond(), resolverManager.resolveJava(leafNode.getMetadata(), true));

        // todo test parent name for typeArgument
    }

    @Test
    public void resolveFieldMetadataTest() {
        FieldMetadata metadata = NodeTestUtils.buildFieldMetadata("test", usualDto.getFirst(),
            false, null, null, false);

        assertEquals("test: UsualDto", metadata.toString());

        metadata.setList(true);
        assertEquals("test: List<UsualDto>", metadata.toString());

        metadata.setList(false);
        metadata.setTypeUID(typeParameter.getFirst());
        assertEquals("test: GenTypeParameter<T>", metadata.toString());

        metadata.setTypeArgumentUID(getTypeByName("integer").getUuid());
        assertEquals("test: GenTypeParameter<Integer>", metadata.toString());

        metadata.setTypeArgumentUID(getTypeByName("long").getUuid());
        assertEquals("test: GenTypeParameter<Long>", metadata.toString());

        metadata.setTypeArgumentUID(usualDto.getFirst());
        assertEquals("test: GenTypeParameter<UsualDto>", metadata.toString());
    }

    private Type getTypeByName(String name) {
        return ModuleTestData.autoCleanState.getTypeManager()
            .getPrimitives().values().stream()
            .filter(t -> t.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(name + " type not found"));
    }
}
