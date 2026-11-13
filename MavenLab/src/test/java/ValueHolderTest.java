import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValueHolderTest {
    ValueHolder valueHolder;
    @BeforeEach
    public void setUp() throws Exception {
        valueHolder = new ValueHolder("CS203");
    }

    @Test
    public void testJsonConversion() {
        String jsonString = valueHolder.toJson();
        ValueHolder valueHolderJson = ValueHolder.fromJson(jsonString);

        Assertions.assertEquals(valueHolder, valueHolderJson);
    }
}
