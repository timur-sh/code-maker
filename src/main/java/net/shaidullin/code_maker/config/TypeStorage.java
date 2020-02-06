package net.shaidullin.code_maker.config;


import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.type.FieldType;
import net.shaidullin.code_maker.core.type.FieldTypeUtils;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Storage contains primitive types and custom types that are defined in module
 */
public class TypeStorage {

    public static String PRIMITIVES = "Base types";
    public static String MODEL = "Model-class types";
    public static final FieldType UNDEFINED = FieldTypeUtils.buildFieldType(UUID.fromString("778f6103-7223-4cd8-8757-000000000000"),
        "undefined",
        false,
        false,
        Undefined.class.getName());

    private final Map<UUID, FieldType> primitives = new HashMap<>();

    private final Map<ElementNode, Map<UUID, FieldType>> customTypes = new HashMap<>();


    TypeStorage() {
    }

    void initializePrimitiveTypes() {
        if (primitives.size() > 0) {
            return;
        }

        UUID longUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c3291f1");
        primitives.put(longUID, FieldTypeUtils.buildFieldType(longUID, "Long", true, false, Long.class.getName()));

        UUID intUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c3291f2");
        primitives.put(intUID, FieldTypeUtils.buildFieldType(intUID, "Integer", true, false, Integer.class.getName()));

        UUID booleanUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c3291f3");
        primitives.put(booleanUID, FieldTypeUtils.buildFieldType(booleanUID, "Boolean", true, false, Boolean.class.getName()));

        UUID doubleUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c3291f4");
        primitives.put(doubleUID, FieldTypeUtils.buildFieldType(doubleUID, "Double", true, false, Double.class.getName()));

        UUID dateUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c3291f5");
        primitives.put(dateUID, FieldTypeUtils.buildFieldType(dateUID, "Date time", false, true, Date.class.getName()));

        UUID localTimeUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c329151");
        primitives.put(localTimeUID, FieldTypeUtils.buildFieldType(localTimeUID, "LocalTime", false, true, LocalTime.class.getName()));

        UUID bigDecimalUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c3291f6");
        primitives.put(bigDecimalUID, FieldTypeUtils.buildFieldType(bigDecimalUID, "BigDecimal", false, true, BigDecimal.class.getName()));

        UUID guidUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c3291f7");
        primitives.put(guidUID, FieldTypeUtils.buildFieldType(guidUID, "UUID", false, true, UUID.class.getName()));

        UUID stringUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c3291f8");
        primitives.put(stringUID, FieldTypeUtils.buildFieldType(stringUID, "String", false, false, String.class.getName()));

        UUID mapUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c3291f9");
        primitives.put(mapUID, FieldTypeUtils.buildFieldType(mapUID, "Map", false, true, Map.class.getName()));

        UUID objectUID = UUID.fromString("778f6103-7223-4cd8-8757-440b2c3291110");
        primitives.put(objectUID, FieldTypeUtils.buildFieldType(objectUID, "Object", false, false, Object.class.getName()));
    }

    void registerCustomTypes(Map<ElementNode, Map<UUID, FieldType>> types) {
        for (ElementNode elementNode : types.keySet()) {
            if (!customTypes.containsKey(elementNode)) {
                customTypes.put(elementNode, new HashMap<>());
            } else {
                customTypes.get(elementNode).clear();
            }

            Map<UUID, FieldType> typeMap = customTypes.get(elementNode);
            typeMap.putAll(types.get(elementNode));
        }
    }

    /**
     * Returns FieldType if it is found in storage. Otherwise UNDEFINED type will be returned
     *
     * @param uuid
     * @return
     */
    public FieldType getFieldByUID(UUID uuid) {
        if (primitives.containsKey(uuid)) {
            return primitives.get(uuid);
        }

        return customTypes.values().stream()
            .flatMap(l -> l.values().stream())
            .filter(t -> t.getUuid().equals(uuid))
            .findFirst()
            .orElse(UNDEFINED);
    }

    public Map<UUID, FieldType> getPrimitives() {
        return primitives;
    }

    public Map<UUID, FieldType> getTypesByElementNode(ElementNode elementNode) {
        return customTypes.get(elementNode);
    }

    protected Map<UUID, FieldType> getClassesByModule() {
        Map<UUID, FieldType> result = new HashMap<>();
        for (Map<UUID, FieldType> value : customTypes.values()) {
            result.putAll(value);
        }

        return result;
    }


    public static class Undefined {

    }
}
