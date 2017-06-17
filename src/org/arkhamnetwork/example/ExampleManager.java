package org.arkhamnetwork.example;

import org.arkhamnetwork.example.objects.ExampleObject;

import com.redmancometh.redcore.mediators.ObjectManager;

/**
 * 
 * @author Redmancometh
 * This class provides methods to get, save, saveAndPurge, etc objects.
 * 
 * By extending ObjectManager we can satisfy the abstract method RedPlugin.getManager() in ArkhamExample
 */

public class ExampleManager extends ObjectManager<ExampleObject> // Extend the class this manager persists here
{
    public ExampleManager()
    {
        super(ExampleObject.class);
    }

}
