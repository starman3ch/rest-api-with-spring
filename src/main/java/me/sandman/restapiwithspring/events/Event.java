package me.sandman.restapiwithspring.events;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class Event {
    // of = id라 준 이유는 equals와 hashcode를 구현할 때 (안주면 모든 필드를 다 사용하는데) Entity간에 연관관계가 있을때 상호참조하는 관계가
    // 돼버리면 equals와 hashcode를 구현하는 함수 안에서 stackOverflow가 날 수 있음.
    // 그래서 강사는 보통 id만 가지고 쓴다. 다른 것도 넣어도 된다. (of = { "id", "name" }) 이렇게. 하지만 절대로 연관관계에 해당하는 필드는 넣지 말 것.

    // lombok annotion은 meta annotation 으로 묶을 수 없다.

    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    public void update() {
        // update free
        if (this.basePrice == 0 && this.maxPrice == 0) {
            this.free = true;
        } else {
            this.free = false;
        }
        // update offline
        if (this.location == null || this.location.isBlank()) {
            this.offline = false;
        } else {
            this.offline = true;
        }
    }
}
