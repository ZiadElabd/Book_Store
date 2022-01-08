package software.project.backend.Model.builder;

import software.project.backend.Model.Imodel;

public class Director {
    modelFactory factory=new modelFactory();
    Ibuilder builder;
    public Imodel composeModel(String type, String model){
        builder=factory.getModelBuilder(type);

        if (!builder.comopse(model)) return null;
        return builder.getModel();
    }
}
