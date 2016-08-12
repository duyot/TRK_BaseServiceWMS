package com.viettel.vwf5.base.servicecaller.bccs;

import com.viettel.vwf5.base.servicecaller.bccs.XStreamStorage;
import java.util.List;

public final class Mapper {

    private static Mapper instance = new Mapper();

    public static Mapper  alias(List<XStreamStorage> xStreamStorageList,
            Class classType, String aliasName) throws ClassNotFoundException {
        // type = 0;
        alias(xStreamStorageList, classType.getName(), aliasName);
        return instance;

    }

    public static Mapper alias(List<XStreamStorage> xStreamStorageList,
            String classType, String aliasName) throws ClassNotFoundException {
        // type = 0;
        XStreamStorage aliasStore = new XStreamStorage(
                XStreamStorage.ALIAS_TYPE);
        aliasStore.getVariableStorage().add(0, classType);
        aliasStore.getVariableStorage().add(1, aliasName);
        xStreamStorageList.add(aliasStore);
        return instance;

    }

    public static Mapper aliasField(List<XStreamStorage> xStreamStorageList,
            Class classType, String aliasName, String fieldName) throws ClassNotFoundException {
        aliasField(xStreamStorageList,
                classType.getName(), aliasName, fieldName);
        return instance;
    }

     public static Mapper aliasField(List<XStreamStorage> xStreamStorageList,
            String classType, String aliasName, String fieldName)
            throws ClassNotFoundException {

        XStreamStorage aliasStore = new XStreamStorage(
                XStreamStorage.ALIAS_FIELD_TYPE);
        aliasStore.getVariableStorage().add(0, classType);
        aliasStore.getVariableStorage().add(1, aliasName);
        aliasStore.getVariableStorage().add(2, fieldName);
        xStreamStorageList.add(aliasStore);
         return instance;
    }

    public static Mapper addImplicitCollection(
            List<XStreamStorage> xStreamStorageList, Class classType,
            String fieldName) throws ClassNotFoundException {
        addImplicitCollection(
                xStreamStorageList, classType.getName(),
                fieldName);
        return instance;
    }

    public static Mapper  addImplicitCollection(
            List<XStreamStorage> xStreamStorageList, String classType,
            String fieldName) throws ClassNotFoundException {
        XStreamStorage aliasStore = new XStreamStorage(
                XStreamStorage.ALIAS_COLLECTION_TYPE);
        aliasStore.getVariableStorage().add(0, classType);
        aliasStore.getVariableStorage().add(1, fieldName);
        xStreamStorageList.add(aliasStore);
        return instance;
    }

    public static Mapper addImplicitArray(
            List<XStreamStorage> xStreamStorageList, Class classType,
            String fieldName) throws ClassNotFoundException {
        addImplicitArray(xStreamStorageList, classType.getName(), fieldName);
        return instance;
    }

    public static Mapper addImplicitArray(
            List<XStreamStorage> xStreamStorageList, String classType,
            String fieldName) throws ClassNotFoundException {
        XStreamStorage aliasStore = new XStreamStorage(
                XStreamStorage.ALIAS_ARRAY_TYPE);
        aliasStore.getVariableStorage().add(0, classType);
        aliasStore.getVariableStorage().add(1, fieldName);
        xStreamStorageList.add(aliasStore);
        return instance;
    }

    public static Mapper addImplicitMap(List<XStreamStorage> xStreamStorageList,
            String className, String fieldName, Class itemType,
            String keyFieldName) throws ClassNotFoundException {
        addImplicitMap(xStreamStorageList,
                className, fieldName, itemType.getName(),
                keyFieldName);
        return instance;
    }

    public static Mapper addImplicitMap(List<XStreamStorage> xStreamStorageList,
            String className, String fieldName, String itemType,
            String keyFieldName) throws ClassNotFoundException {
        XStreamStorage aliasStore = new XStreamStorage(
                XStreamStorage.ALIAS_MAP_TYPE);
        aliasStore.getVariableStorage().add(0, className);
        aliasStore.getVariableStorage().add(1, fieldName);
        aliasStore.getVariableStorage().add(2, itemType);
        aliasStore.getVariableStorage().add(3, keyFieldName);
        xStreamStorageList.add(aliasStore);
        return instance;
    }

    private void addImplicitMap(List<XStreamStorage> xStreamStorageList,
            String className, String fieldName, String itemName,
            Class itemType, String keyFieldName) throws ClassNotFoundException {
        addImplicitMap(xStreamStorageList,
                className, fieldName, itemName,
                itemType.getName(), keyFieldName);
    }

    private void addImplicitMap(List<XStreamStorage> xStreamStorageList,
            String className, String fieldName, String itemName,
            String itemType, String keyFieldName) throws ClassNotFoundException {
        XStreamStorage aliasStore = new XStreamStorage(
                XStreamStorage.ALIAS_MAP__FIELD_TYPE);
        aliasStore.getVariableStorage().add(0, className);
        aliasStore.getVariableStorage().add(1, fieldName);
        aliasStore.getVariableStorage().add(2, itemName);
        aliasStore.getVariableStorage().add(3, itemType);
        aliasStore.getVariableStorage().add(4, keyFieldName);
        xStreamStorageList.add(aliasStore);

    }

}
