public class VarObject {

    private String name = "";
    private int value = 0;

    public VarObject(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public void setValue(int value) throws Exception {
        this.value = value;
        checkValue();
    }

    public int getValue(){
        return value;
    }

    public void incrValue(int value) throws Exception {
        this.value += value;
        checkValue();
    }

    private void checkValue() throws Exception {
        if (this.value < 0){
            throw new Exception(this.name + " cannot have a negative value");
        }
    }

    public String getName(){
        return name;
    }

}
