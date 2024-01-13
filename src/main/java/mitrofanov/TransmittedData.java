package mitrofanov;

import lombok.extern.slf4j.Slf4j;

public record TransmittedData(
        String text,
        String command
) {


}