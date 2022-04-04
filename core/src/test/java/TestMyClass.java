import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.core.MyClass;

public class TestMyClass {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, MyClass.addition(2, 2));
    }
}
