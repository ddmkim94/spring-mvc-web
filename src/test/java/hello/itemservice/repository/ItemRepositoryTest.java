package hello.itemservice.repository;

import hello.itemservice.domain.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    private final ItemRepository itemRepository = new ItemRepository();

    // 각 테스트가 끝나면 실행되는 메서드
    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    @DisplayName("상품 저장 & 조회 테스트!")
    void saveItemTest() throws Exception{
        Item item = new Item("아이폰 MAX PRO", 2000000, 100);
        Item saveItem = itemRepository.save(item);
        Item findItem = itemRepository.findById(item.getId());

        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    @DisplayName("전체 상품 조회 테스트!!")
    void itemListTest() throws Exception{
        Item item1 = new Item("아이폰 MAX PRO1", 2000000, 100);
        Item item2 = new Item("아이폰 MAX PRO2", 3000000, 200);
        Item item3 = new Item("아이폰 MAX PRO3", 4000000, 300);
        Item item4 = new Item("아이폰 MAX PRO4", 5000000, 400);

        Item saveItem1 = itemRepository.save(item1);
        Item saveItem2 = itemRepository.save(item2);
        Item saveItem3 = itemRepository.save(item3);
        Item saveItem4 = itemRepository.save(item4);

        List<Item> items = itemRepository.findAll();

        assertThat(items.size()).isEqualTo(4);
        assertThat(items).contains(saveItem1, saveItem2, saveItem3, saveItem4);
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateItemTest() throws Exception{
        Item item = new Item("아이폰 MAX PRO1", 2000000, 100);
        Item saveItem = itemRepository.save(item);
        Long itemId = saveItem.getId();

        Item newItem = new Item("아이폰8", 500000, 200);

        itemRepository.update(itemId, newItem);
        Item findItem = itemRepository.findById(itemId);

        assertThat(findItem.getItemName()).isEqualTo(newItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(newItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(newItem.getQuantity());
    }
}