package org.arkhamnetwork.example.objects;

import java.util.UUID;

import com.redmancometh.redcore.Defaultable;

public class ExampleObject implements Defaultable<UUID> // The generic passed here corresponds to the key the object is stored by!
{
    private int exampleValue;

    @Override
    public void setDefaults(UUID uuid)
    {
        this.exampleValue = 0;
    }

    public int getExampleValue()
    {
        return exampleValue;
    }

    public void setExampleValue(int exampleValue)
    {
        this.exampleValue = exampleValue;
    }

}
