package library.infrastructure;

import library.domain.IMemberRepository;
import library.domain.Member;

import java.util.*;

public class InMemoryMemberRepository implements IMemberRepository {
    private final Map<String, Member> members = new HashMap<>();

    @Override
    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    @Override
    public Optional<Member> findById(String id) {
        return Optional.ofNullable(members.get(id));
    }

    @Override
    public List<Member> findAllMembers() {
        return new ArrayList<>(members.values());
    }
}
