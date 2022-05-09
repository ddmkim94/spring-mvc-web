package hello.itemservice.repository;

import hello.itemservice.domain.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRepository {

    /**
     * 1. 저장소
     * 2. Sequence
     */
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    // 1. 상품 저장
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    // 2. 상품 단일 검색
    public Item findById(Long itemId) {
        return store.get(itemId);
    }

    // 3. 상품 리스트 검색
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 4. 상품 수정
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    // 저장소 청소!!
    public void clearStore() {
        store.clear();
    }
}