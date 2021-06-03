package fei.stuba.bp.rigo.preteky.models.roles;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Roles {
    private Boolean supervisor = false;
    private Boolean registered = false;
    private Boolean admin = false;
}
