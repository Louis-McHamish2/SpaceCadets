public class conditions {

    public static boolean testCondition(String[] condition) throws Exception {
        if (condition[1].equals("not")) {
            return valueOf(condition[0]) != valueOf(condition[2]);
        } else if (condition[1].equals("is")) {
            return valueOf(condition[0]) == valueOf(condition[2]);
        }
        throw new Exception("Keyword " + condition[1] + " not recognised");
}

    public static int valueOf(String var){
        try{
            // is value
            int number = Integer.parseInt(var);
            return number;
        }
        catch (NumberFormatException ex){
            // is variable name
            VarObject variable = (VarObject) Main.findVariable(var);
            return variable.getValue();
        }
    }
}
