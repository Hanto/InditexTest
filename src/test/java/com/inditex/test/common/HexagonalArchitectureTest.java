package com.inditex.test.common;// Created by jhant on 07/06/2022.

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = HexagonalArchitectureTest.CORE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class})
public class HexagonalArchitectureTest
{
    static final String CORE_PACKAGE = "com.inditex.test";
    private static final String DOMAIN = "..domain..";
    private static final String ADAPTERS = "..adapter..";
    private static final String APPLICATION = "..application..";
    private static final String CONFIGURATION = "..configuration..";

    // GENERAL:
    //--------------------------------------------------------------------------------------------------------

    @ArchTest
    public void packagesShouldBeFreeOfCyles(JavaClasses importedClasses)
    {
        ArchRule rule = slices().matching(CORE_PACKAGE + ".(*)..").should()
            .beFreeOfCycles();
        rule.check(importedClasses);
    }

    // DOMAIN LAYER:
    //--------------------------------------------------------------------------------------------------------

    @ArchTest
    public void domainShouldNotDependOnAdapters(JavaClasses importedClasses)
    {
        ArchRule myRule = noClasses().that().resideInAPackage(DOMAIN).should()
            .dependOnClassesThat().resideInAPackage(ADAPTERS);
        myRule.check(importedClasses);
    }

    @ArchTest
    public void domainShouldNotDependOnApplication(JavaClasses importedClasses)
    {
        ArchRule myRule = noClasses().that().resideInAPackage(DOMAIN).should()
            .dependOnClassesThat().resideInAPackage(APPLICATION);
        myRule.check(importedClasses);
    }

    @ArchTest
    public void domainShouldNotDependOnConfig(JavaClasses importedClasses)
    {
        ArchRule myRule = noClasses().that().resideInAPackage(DOMAIN).should()
            .dependOnClassesThat().resideInAPackage(CONFIGURATION);
        myRule.check(importedClasses);
    }

    // APPLICATION LAYER:
    //--------------------------------------------------------------------------------------------------------

    @ArchTest
    public void applicationShouldNotDependOnAdapters(JavaClasses importedClasses)
    {
        ArchRule myRule = noClasses().that().resideInAPackage(APPLICATION).should()
            .dependOnClassesThat().resideInAPackage(ADAPTERS);
        myRule.check(importedClasses);

    }

    @ArchTest
    public void applicationShouldNotDependOnConfig(JavaClasses importedClasses)
    {
        ArchRule myRule = noClasses().that().resideInAPackage(APPLICATION).should()
            .dependOnClassesThat().resideInAPackage(CONFIGURATION);
        myRule.check(importedClasses);
    }
}