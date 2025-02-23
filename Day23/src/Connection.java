import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Connection {
    Set<Computer> participants;

    public Connection(String input) throws IllegalArgumentException{
        participants = Arrays.stream(input.split("-"))
                .map(Computer::new).collect(Collectors.toSet());
        if (participants.size() != 2)
            throw new IllegalArgumentException("Expected 2 computers, found " + participants.size());
    }

    public Connection(Computer c1, Computer c2) {
        participants = new HashSet<>();
        participants.add(c1);
        participants.add(c2);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Connection that)) return false;
        return Objects.equals(participants, that.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(participants);
    }
}
