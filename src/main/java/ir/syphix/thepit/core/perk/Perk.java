package ir.syphix.thepit.core.perk;

import java.util.List;

public interface Perk {

    public String id();
    public String displayName();
    public List<String> description();
    public double price();

}
