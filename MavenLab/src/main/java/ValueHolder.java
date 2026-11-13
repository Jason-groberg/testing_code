import com.google.gson.Gson;

public class ValueHolder {
    private String value;

    public ValueHolder(String value) {
        this.value = value;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static ValueHolder fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, ValueHolder.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueHolder that = (ValueHolder) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
