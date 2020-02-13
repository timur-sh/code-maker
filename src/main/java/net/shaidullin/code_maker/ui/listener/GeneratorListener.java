package net.shaidullin.code_maker.ui.listener;

import com.intellij.openapi.ui.Messages;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.PackageNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GeneratorListener implements ActionListener {
    private final JTree tree;
    private final ApplicationState state;
//    private final GeneratorService generator;

    public GeneratorListener(JTree tree, ApplicationState state) {
        this.tree = tree;
        this.state = state;
//        generator = GeneratorService.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath path = tree.getSelectionPath();
        PackageNode packageNode = (PackageNode) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
        state.refreshState();

//        List<ClassNode> classNodes = configuration.getClasses().get(packageNode);
//        if (classNodes.isEmpty()) {
//            return;
//        }
//        if (!createDirectories(packageNode, configuration)) {
//            return;
//        }
//
//        switch (packageNode.getClassifierEnum()) {
//            case ENTITY:
//                generateEntity(packageNode, classNodes, configuration);
//                break;
//            case DOMAIN:
//                generateDomain(packageNode, classNodes, configuration);
//                generateTypeScript(packageNode, configuration);
//                break;
//            case SERVICE:
//                generateService(packageNode, classNodes, configuration);
//                generateTypeScript(packageNode, configuration);
//                break;
//            default:
//                throw new RuntimeException("Unsupported classifier = " + packageNode.getClassifierEnum().getCode());
//        }

        Messages.showInfoMessage(String.format(
            "Generation completed for '%s' package", packageNode.getSystemName()
        ), "Generation");
    }
//
//    private void generateService(PackageNode packageNode, List<ClassNode> classNodes, ApplicationConfiguration configuration) {
//        String basePackageName = String.format("%s.%s", packageNode.getParent().getParent().getPackageName(), packageNode.getSystemName());
//
//        String servicePackage = String.format("%s.%s", basePackageName, packageNode.getParent().getSystemName().toLowerCase());
//        String implPackage = String.format("%s.%s.impl", basePackageName, packageNode.getParent().getSystemName().toLowerCase());
//        String testStubPackage = String.format("%s.test", basePackageName);
//        String endpointPackage = String.format("%s.endpoint", basePackageName);
//        String apiPackage = String.format("%s.api", basePackageName);
//
//        ModuleNode moduleNode = packageNode.getParent().getParent();
//
//        String generationDirectory = buildPathForDirectory(servicePackage, moduleNode.getSystemName(), configuration, null);
//        createDirectories(generationDirectory, configuration);
//        String generationDirectoryImpl = buildPathForDirectory(implPackage, moduleNode.getSystemName(), configuration, null);
//        createDirectories(generationDirectoryImpl, configuration);
//        String generationDirectoryTest = buildPathForDirectory(testStubPackage, moduleNode.getSystemName(), configuration, null);
//        createDirectories(generationDirectoryTest, configuration);
//        String generationDirectoryEndpoint = buildPathForDirectory(endpointPackage, moduleNode.getSystemName(), configuration, null);
//        createDirectories(generationDirectoryEndpoint, configuration);
//        String generationDirectoryApi = buildPathForDirectory(apiPackage, moduleNode.getSystemName(), configuration, null);
//        createDirectories(generationDirectoryApi, configuration);
//
//        String mainPackage = buildPackageName(packageNode);
//
//        for (ClassNode classNode : classNodes) {
//            List<String> importingPackages = new ArrayList<>();
//            Map<String, String> packagesForImplementations = new HashMap<>();
//            packagesForImplementations.put(GeneratorService.SERVICE, servicePackage);
//            packagesForImplementations.put(GeneratorService.IMPL, implPackage);
//            packagesForImplementations.put(GeneratorService.TEST, testStubPackage);
//            packagesForImplementations.put(GeneratorService.ENDPOINT, endpointPackage);
//            packagesForImplementations.put(GeneratorService.CLIENT, apiPackage);
//
//            ServiceMetadata metadata = ((ServiceMetadata) classNode.getMetadata());
//            ServiceModel model = new ServiceModel();
//            model.setSystemName(metadata.getSystemName());
//            model.setBaseUrl(metadata.getBaseUrl());
//            model.setOperations(metadata.getOperations());
//            model.setDescription(metadata.getDescription());
//            model.setPackageNode(packageNode);
//
//            for (OperationMetadata operation : metadata.getOperations()) {
//                importingPackages.addAll(operation.getImportingPackages());
//            }
//
//            Map<String, Object> root = new HashMap<>();
//            root.put(GeneratorService.MODEL, model);
//            root.put(GeneratorService.IMPORTING_PACKAGES, importingPackages.stream().distinct().collect(Collectors.toList()));
//            root.put(GeneratorService.PACKAGE, mainPackage);
//
//            Map<String, String> generatedResults = generator.renderService(classNode.getSystemName(), root, packagesForImplementations);
//            for (String code : generatedResults.keySet()) {
//                String directory;
//                String fileName = model.getSystemName() + code;
//                switch (code) {
//                    case GeneratorService.SERVICE:
//                        directory = generationDirectory;
//                        fileName = model.getSystemName();
//                        break;
//                    case GeneratorService.TEST:
//                        directory = generationDirectoryTest;
//                        break;
//                    case GeneratorService.IMPL:
//                        directory = generationDirectoryImpl;
//                        break;
//                    case GeneratorService.ENDPOINT:
//                        directory = generationDirectoryEndpoint;
//                        break;
//                    case GeneratorService.CLIENT:
//                        directory = generationDirectoryApi;
//                        break;
//                    default:
//                        throw new RuntimeException("Unsupported code = " + code);
//                }
//
//                FileHelper.saveContent(
//                    FileHelper.buildAbsoluteFileName(directory, fileName, "java"),
//                    generatedResults.get(code)
//                );
//            }
//
//
//            generateAudit(metadata, packageNode, configuration);
//        }
//
//        generateAuditOperationPopulate(packageNode.getParent(), configuration);
//    }
//
//    private void generateTypeScript(PackageNode packageNode, ApplicationConfiguration configuration) {
//        ModuleNode moduleNode = packageNode.getParent().getParent();
//
//        List<ElementNode> elementNodes = configuration.getElements().get(moduleNode);
//
//        List<ServiceModel> serviceModels = new ArrayList<>();
//        List<DomainModel> domainModels = new ArrayList<>();
//
//        Map<String, Object> serviceFactory = new HashMap<>();
//        serviceFactory.put(GeneratorService.TS_MODULE_NAME, moduleNode.getSystemName());
//        List<String> serviceNames = new ArrayList<>();
//        serviceFactory.put(GeneratorService.TS_SERVICE_NAME, serviceNames);
//
//        for (ElementNode elementNode : elementNodes) {
//            if (ClassifierEnum.SERVICE.equals(ClassifierEnum.createByCode(elementNode.getPackageName()))) {
//                List<String> allServiceNames = configuration.getClasses().values().stream()
//                    .flatMap(Collection::stream)
//                    .filter(classNode -> classNode.getClassifierEnum().equals(ClassifierEnum.SERVICE))
//                    .filter(classNode -> classNode.getParent().getParent().getParent().equals(moduleNode))
//                    .map(ClassNode::getSystemName)
//                    .collect(Collectors.toList());
//                serviceNames.addAll(allServiceNames);
//
//                List<ClassNode> serviceClassNodes = configuration.getClasses().values().stream()
//                    .flatMap(Collection::stream)
//                    .filter(classNode -> classNode.getClassifierEnum().equals(ClassifierEnum.SERVICE))
//                    .filter(classNode -> classNode.getParent().getPackageName().equals(packageNode.getPackageName()))
//                    .filter(classNode -> classNode.getParent().getParent().getParent().equals(moduleNode))
//                    .collect(Collectors.toList());
//
//                for (ClassNode serviceClassNode : serviceClassNodes) {
//                    ServiceMetadata serviceMetadata = (ServiceMetadata) serviceClassNode.getMetadata();
//
//                    ServiceModel serviceModel = new ServiceModel();
//                    serviceModel.setBaseUrl(serviceMetadata.getBaseUrl());
//                    serviceModel.setDescription(serviceMetadata.getDescription());
//                    serviceModel.setSystemName(serviceMetadata.getSystemName());
//                    serviceModel.setOperations(serviceMetadata.getOperations());
//
//                    serviceModels.add(serviceModel);
//                }
//            }
//
//            if (ClassifierEnum.DOMAIN.equals(ClassifierEnum.createByCode(elementNode.getPackageName()))) {
//                List<ClassNode> domainClassNodes = configuration.getClasses().values().stream()
//                    .flatMap(Collection::stream)
//                    .filter(classNode -> classNode.getClassifierEnum().equals(ClassifierEnum.DOMAIN))
//                    .filter(classNode -> classNode.getParent().getPackageName().equals(packageNode.getPackageName()))
//                    .filter(classNode -> classNode.getParent().getParent().getParent().equals(moduleNode))
//                    .collect(Collectors.toList());
//
//                for (ClassNode domainClassNode : domainClassNodes) {
//                    DomainMetadata domainMetadata = (DomainMetadata) domainClassNode.getMetadata();
//
//                    DomainModel domainModel = new DomainModel();
//                    domainModel.setSystemName(domainMetadata.getSignatureClassName());
//
//                    if (domainMetadata.getParentUID() != null) {
//                        ClassNode parentClassNode = configuration.getClassByUID(domainMetadata.getParentUID());
//                        if (!parentClassNode.equals(UNDEFINED_CLASS)) {
//                            domainModel.setParent(parentClassNode.getMetadata().getSystemName());
//                        }
//
//                    }
//
//                    domainModel.setFields(domainMetadata.getFields());
//
//                    domainModels.add(domainModel);
//                }
//            }
//        }
//
//        String generationDirectory = buildPathForDirectory("typescript", moduleNode.getSystemName(), configuration, null);
//        createDirectories(generationDirectory, configuration);
//
//        Map<String, Object> root = new HashMap<>();
//        root.put(GeneratorService.TS_SERVICES, serviceModels);
//        root.put(GeneratorService.TS_DOMAINS, domainModels);
//
//        String generatedServicesCode = generator.renderTypeScriptServices(root);
//        String generatedFactory = generator.renderTypeScriptFactory(serviceFactory);
//
//        FileHelper.saveContent(
//            FileHelper.buildAbsoluteFileName(generationDirectory, packageNode.getPackageName()+"-api", "ts"),
//            generatedServicesCode
//        );
//
//        FileHelper.saveContent(
//            FileHelper.buildAbsoluteFileName(generationDirectory, moduleNode.getSystemName().toLowerCase()+"-factory", "ts"),
//            generatedFactory
//        );
//    }
//
//    private void generateAuditOperationPopulate(ElementNode elementNode, ApplicationConfiguration configuration) {
//        List<PackageNode> packageNodes = configuration.getPackages().get(elementNode);
//
//        Map<ServiceMetadata, List<AuditOperation>> auditableOperations = new HashMap<>();
//
//        for (PackageNode packageNode : packageNodes) {
//            List<ServiceMetadata> services = configuration.getClasses().get(packageNode).stream()
//                .filter(c -> c.getMetadata() instanceof ServiceMetadata)
//                .map(c -> (ServiceMetadata) c.getMetadata())
//                .collect(Collectors.toList());
//
//            for (ServiceMetadata service : services) {
//                auditableOperations.put(service, new ArrayList<>());
//
//                for (OperationMetadata operation : service.getOperations()) {
//                    if (operation.isAuditable()) {
//                        auditableOperations.get(service).add(assembleAuditOperation(operation, service));
//                    }
//                }
//            }
//        }
//
//        if (!auditableOperations.isEmpty()) {
//            AuditEntryModel auditEntryModel = new AuditEntryModel();
//            for (ServiceMetadata serviceMetadata : auditableOperations.keySet()) {
//                if (auditableOperations.get(serviceMetadata).isEmpty()) {
//                    continue;
//                }
//
//                AuditEntryListModel listModel = new AuditEntryListModel();
//                listModel.setService(serviceMetadata);
//                listModel.getOperations().addAll(auditableOperations.get(serviceMetadata));
//
//                auditEntryModel.getEntries().add(listModel);
//            }
//
//            Map<String, Object> entryRoot = new HashMap<>();
//            entryRoot.put(GeneratorService.MODEL, auditEntryModel);
//
//            String entryAuditPath = buildPathForDirectory("resources.db.populate", elementNode.getParent().getSystemName(), configuration, null);
//            createDirectories(entryAuditPath, configuration);
//            String generatedCode = generator.renderAuditEntryDB(entryRoot);
//            FileHelper.saveContent(
//                FileHelper.buildAbsoluteFileName(entryAuditPath, "audit_operation_populate", "sql"),
//                generatedCode
//            );
//        }
//    }
//
//    private AuditOperation assembleAuditOperation(OperationMetadata operation, ServiceMetadata serviceMetadata) {
//        AuditOperation auditOperation = new AuditOperation();
//        auditOperation.setSystemName(operation.getSystemName() + "Operation");
//        auditOperation.setOperationId(String.valueOf(operation.getAuditId()));
//
//        String operationName = String.format(
//            "%s.%s",
//            serviceMetadata.getSystemName(),
//            operation.getSystemName()
//        );
//        auditOperation.setOperationName(operationName);
//        auditOperation.setOperationDescription(operation.getDescription());
//
//        return auditOperation;
//    }
//
//    private List<AuditOperation> generateAudit(ServiceMetadata serviceMetadata, PackageNode packageNode, ApplicationConfiguration configuration) {
//        String auditPackage = String.format("%s.%s.audit", packageNode.getParent().getParent().getPackageName(), packageNode.getSystemName());
//        String operationPackage = String.format("%s.operation", auditPackage);
//
//        AuditModel auditModel = new AuditModel();
//        auditModel.setSystemName(BavlexStringUtils.capitalizeFirstLetter(packageNode.getSystemName()) + "AuditFactory");
//        List<String> importingPackages = new ArrayList<>();
//
//        for (OperationMetadata operation : serviceMetadata.getOperations()) {
//            if (operation.isAuditable()) {
//                AuditOperation auditOperation = assembleAuditOperation(operation, serviceMetadata);
//                auditModel.getOperations().add(assembleAuditOperation(operation, serviceMetadata));
//
//                importingPackages.add(String.format("%s.%s", operationPackage, BavlexStringUtils.capitalizeFirstLetter(auditOperation.getSystemName())));
//            }
//        }
//
//        if (CollectionUtils.isEmpty(auditModel.getOperations())) {
//            return null;
//        }
//
//        ModuleNode moduleNode = packageNode.getParent().getParent();
//
//        String auditDirectory = buildPathForDirectory(auditPackage, moduleNode.getSystemName(), configuration, null);
//        createDirectories(auditDirectory, configuration);
//        String operationDirectory = buildPathForDirectory(operationPackage, moduleNode.getSystemName(), configuration, null);
//        createDirectories(operationDirectory, configuration);
//
//        Map<String, Object> root = new HashMap<>();
//        root.put(GeneratorService.MODEL, auditModel);
//        root.put(GeneratorService.IMPORTING_PACKAGES, importingPackages.stream().distinct().collect(Collectors.toList()));
//        root.put(GeneratorService.PACKAGE, auditPackage);
//
//        String generatedCode = generator.renderAuditFactory(root);
//        FileHelper.saveContent(
//            FileHelper.buildAbsoluteFileName(auditDirectory, auditModel.getSystemName(), "java"),
//            generatedCode
//        );
//
//        for (AuditOperation operation : auditModel.getOperations()) {
//            Map<String, Object> operationRoot = new HashMap<>();
//            operationRoot.put(GeneratorService.MODEL, operation);
//            operationRoot.put(GeneratorService.PACKAGE, operationPackage);
//
//            generatedCode = generator.renderAuditOperation(operationRoot);
//            FileHelper.saveContent(
//                FileHelper.buildAbsoluteFileName(
//                    operationDirectory,
//                    BavlexStringUtils.capitalizeFirstLetter(operation.getSystemName()),
//                    "java"
//                ),
//                generatedCode
//            );
//
//        }
//        return auditModel.getOperations();
//    }
//
//    private void generateDomain(PackageNode packageNode, List<ClassNode> classNodes, ApplicationConfiguration configuration) {
//        String generationPath = buildPathForDirectory(packageNode, configuration);
//        String mainPackage = buildPackageName(packageNode);
//
//        String domainInterface = packageNode.getParent().getParent().getDomainInterface();
//        String cacheInterface = packageNode.getParent().getParent().getCacheInterface();
//
//
//        for (ClassNode classNode : classNodes) {
//            List<String> importingPackages = new ArrayList<>();
//
//
//            DomainMetadata metadata = ((DomainMetadata) classNode.getMetadata());
//            DomainModel model = new DomainModel();
//            model.setSystemName(metadata.getSystemName());
//            model.setSignatureClassName(metadata.getSignatureClassName());
//
//            if (metadata.getParentUID() != null) {
//                ClassNode parentClassNode = configuration.getClassByUID(metadata.getParentUID());
//                if (!parentClassNode.equals(UNDEFINED_CLASS)) {
//                    String parentPackageName = buildPackageName(parentClassNode.getParent(), parentClassNode);
//                    importingPackages.add(parentPackageName);
//                    model.setParent(parentClassNode.getMetadata().getSystemName());
//                }
//
//            } else if (StringUtils.isNoneEmpty(domainInterface) && !metadata.isCachable()) {
//                String parentInterface = BavlexStringUtils.getLastPartOfClassName(domainInterface);
//                model.getInterfaces().add(parentInterface);
//                importingPackages.add(domainInterface);
//            }
//
//            if (metadata.isCachable() && StringUtils.isNoneEmpty(cacheInterface)) {
//                String cachableInterfaceName = BavlexStringUtils.getLastPartOfClassName(cacheInterface);
//                model.getInterfaces().add(cachableInterfaceName);
//                importingPackages.add(cacheInterface);
//
//            }
//
//            importingPackages.addAll(metadata.getImportingPackages());
//            importingPackages = importingPackages.stream().distinct().collect(Collectors.toList());
//
//            model.setFields(metadata.getFields());
//
//            Map<String, Object> root = new HashMap<>();
//            root.put(GeneratorService.MODEL, model);
//            root.put(GeneratorService.IMPORTING_PACKAGES, importingPackages);
//            root.put(GeneratorService.PACKAGE, mainPackage);
//
//            String generationCode = generator.renderDomain(root);
//            FileHelper.saveContent(
//                FileHelper.buildAbsoluteFileName(generationPath, classNode.getSystemName(), "java"),
//                generationCode
//            );
//
//            if (metadata.isCachable()) {
//                generateCache(metadata, configuration);
//            }
//        }
//    }
//
//    private void generateCache(DomainMetadata domainMetadata, ApplicationConfiguration configuration) {
//        List<String> importingPackages = new ArrayList<>();
//        PackageNode packageNode = domainMetadata.getPackageNode();
//        String mainPackage = String.format("%s.%s.cache", packageNode.getParent().getParent().getPackageName(), packageNode.getSystemName());
//
//        ModuleNode moduleNode = packageNode.getParent().getParent();
//        String generationPath = buildPathForDirectory(mainPackage, moduleNode.getSystemName(), configuration, null);
//
//        if (!createDirectories(generationPath, configuration)) {
//            Messages.showWarningDialog("Can't create a directory 'cache'", "Warning");
//            return;
//        }
//
//        if (!domainMetadata.getCacheKeyType().isPrimitive()) {
//            importingPackages.add(domainMetadata.getCacheKeyType().getClz());
//        }
//
//
//        String domainPackage = buildPackageName(packageNode) + "." + domainMetadata.getSystemName();
//        importingPackages.add(domainPackage);
//
//        CacheModel cacheModel = new CacheModel();
//        cacheModel.setKey(domainMetadata.getCacheKeyType().getSystemName(false));
//        cacheModel.setValue(domainMetadata.getSystemName());
//        cacheModel.setSystemName(domainMetadata.getSystemName() + "Cache");
//
//        Map<String, Object> root = new HashMap<>();
//        root.put(GeneratorService.MODEL, cacheModel);
//        root.put(GeneratorService.IMPORTING_PACKAGES, importingPackages);
//        root.put(GeneratorService.PACKAGE, mainPackage);
//
//        String generationCode = generator.renderCache(root);
//        FileHelper.saveContent(
//            FileHelper.buildAbsoluteFileName(generationPath, cacheModel.getSystemName(), "java"),
//            generationCode
//        );
//    }
//
//    private void generateEntity(PackageNode packageNode, List<ClassNode> classNodes, ApplicationConfiguration configuration) {
//        String generationPath = buildPathForDirectory(packageNode, configuration);
//        String mainPackage = buildPackageName(packageNode);
//
//        String parentEntityPackage = packageNode.getParent().getParent().getParentEntity();
//
//        for (ClassNode classNode : classNodes) {
//            List<String> importingPackages = new ArrayList<>();
//
//            EntityMetadata metadata = ((EntityMetadata) classNode.getMetadata());
//            EntityModel model = new EntityModel();
//            model.setSystemName(metadata.getSystemName() + "Entity");
//            model.setTenantEntity(metadata.isTenantEntity());
//            model.setFields(metadata.getFields());
//            model.setEntityName(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, metadata.getSystemName()));
//
//            if (metadata.getParentUID() != null) {
//                ClassNode parentClassNode = configuration.getClassByUID(metadata.getParentUID());
//                if (!parentClassNode.equals(UNDEFINED_CLASS)) {
//                    String parentPackageName = buildPackageName(parentClassNode.getParent(), parentClassNode);
//                    importingPackages.add(parentPackageName);
//                    model.setParent(parentClassNode.getMetadata().getSystemName());
//                }
//
//
//            } else if (StringUtils.isNoneEmpty(parentEntityPackage)) {
//                String parentEntity = BavlexStringUtils.getLastPartOfClassName(parentEntityPackage);
//                model.setParent(parentEntity);
//                importingPackages.add(parentEntityPackage);
//            }
//
//
//            importingPackages.addAll(metadata.getImportingPackages());
//            importingPackages = importingPackages.stream().distinct().collect(Collectors.toList());
//
//            Map<String, Object> root = new HashMap<>();
//            root.put(GeneratorService.MODEL, model);
//            root.put(GeneratorService.IMPORTING_PACKAGES, importingPackages);
//            root.put(GeneratorService.PACKAGE, mainPackage);
//
//            String generationCode = generator.renderEntity(root);
//            FileHelper.saveContent(
//                FileHelper.buildAbsoluteFileName(generationPath, model.getSystemName(), "java"),
//                generationCode
//            );
//        }
//
//        renderHibernateCfg(packageNode.getParent().getParent(), configuration);
//    }
//
//    private void renderHibernateCfg(ModuleNode moduleNode, ApplicationConfiguration configuration) {
//        String generationPath = buildPathForDirectory("resources", moduleNode.getSystemName(), configuration, null);
//
//        if (!createDirectories(generationPath, configuration)) {
//            Messages.showWarningDialog("Can't create a directory 'resources'", "Warning");
//            return;
//        }
//
//        List<ElementNode> elementNodes = configuration.getElements().get(moduleNode).stream()
//            .filter(e -> ClassifierEnum.ENTITY.equals(ClassifierEnum.createByCode(e.getPackageName())))
//            .collect(Collectors.toList());
//
//        List<String> entityClasses = configuration.getClasses().values().stream()
//            .flatMap(Collection::stream)
//            .filter(c -> elementNodes.contains(c.getParent().getParent()))
//            .map(c -> buildPackageName(c.getParent(), c))
//            .collect(Collectors.toList());
//
//
//        Map<String, Object> root = new HashMap<>();
//        root.put(GeneratorService.ENTITIES, entityClasses);
//
//        String generationCode = generator.renderHibernateCfg(root);
//        FileHelper.saveContent(
//            FileHelper.buildAbsoluteFileName(generationPath, "hibernate.cfg", "xml"),
//            generationCode
//        );
//    }
//
//    private boolean createDirectories(PackageNode packageNode, ApplicationConfiguration configuration, String nested) {
//        String pathName = buildPathForDirectory(packageNode, configuration, nested);
//        return createDirectories(pathName, configuration);
//    }
//
//    private boolean createDirectories(String pathName, ApplicationConfiguration configuration) {
//        if (configuration.getGeneratePath() == null) {
//            Messages.showErrorDialog("Generation path not specified", "Error");
//            return false;
//        }
//
//        Path path = Paths.get(pathName);
//
//        try {
//            Files.createDirectories(path);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return true;
//    }
//
//    private boolean createDirectories(PackageNode packageNode, ApplicationConfiguration configuration) {
//        return createDirectories(packageNode, configuration, null);
//    }
//
//    private String buildPathForDirectory(PackageNode packageNode, ApplicationConfiguration configuration, String nested) {
//        ModuleNode moduleNode = packageNode.getParent().getParent();
//        String packageName = buildPackageName(packageNode);
//
//        return buildPathForDirectory(packageName, moduleNode.getSystemName(), configuration, nested);
//    }
//
//    private String buildPathForDirectory(String packageName, String moduleName, ApplicationConfiguration configuration, String nested) {
//
//        String absolutePath = String.format(
//            "%s%s%s%s%s",
//            configuration.getGeneratePath(),
//            File.separator,
//            BavlexStringUtils.capitalizeFirstLetter(moduleName),
//            File.separator,
//            packageName.replace(".", "\\")
//        );
//
//        if (StringUtils.isEmpty(nested)) {
//            return absolutePath;
//        }
//
//        return String.format(
//            "%s%s%s",
//            absolutePath,
//            File.separator,
//            nested
//        );
//    }
//
//    private String buildPathForDirectory(PackageNode packageNode, ApplicationConfiguration configuration) {
//        return buildPathForDirectory(packageNode, configuration, null);
//    }
//
//    public static String buildPackageName(PackageNode packageNode) {
//        return String.format("%s.%s.%s", packageNode.getParent().getParent().getPackageName(), packageNode.getSystemName(), packageNode.getParent().getSystemName().toLowerCase());
//    }
//
//    public static String buildPackageName(PackageNode packageNode, ClassNode classNode) {
//        return String.format("%s.%s", buildPackageName(packageNode), classNode.getSystemName());
//    }
}
