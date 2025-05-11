package library.domain;

import java.util.List;
import java.util.Optional;

public interface IMemberRepository {
    void addMember(Member member);
    Optional<Member> findById(String id);
    List<Member> findAllMembers();
}
