import principal.models.Solicitud;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final List<Solicitud> solicitudes = new ArrayList<>();

    public static void add(Solicitud s) { solicitudes.add(s); }
    public static List<Solicitud> getAll() { return new ArrayList<>(solicitudes); }
    public static Solicitud findById(int id) {
        for (Solicitud s : solicitudes) if (s.getId() == id) return s;
        return null;
    }
}
