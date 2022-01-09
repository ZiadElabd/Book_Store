package software.project.backend.Model.builder;

public class modelFactory {
    public Ibuilder getModelBuilder (String name){
        Ibuilder builder = null;
        if(name.equals(null)) return null;
        else if(name.equals("user")){
            builder=new userBuilder();
        }else if(name.equals("book")){
            builder=new bookBuilder();
        }
        return builder;
    }
}
