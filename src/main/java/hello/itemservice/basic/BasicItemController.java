package hello.itemservice.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final 필드를 파라미터로 가진 생성자를 만들어줌
public class BasicItemController {

    private final ItemRepository itemRepository;

    @PostConstruct
    void init() {
        itemRepository.save(new Item("날씨의 아이", 10000, 10));
        itemRepository.save(new Item("너의 이름은", 20000, 20));
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId,
                       @RequestParam(value = "status", required = false) boolean status,
                       Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        model.addAttribute("status", status);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /** 요청 파라미터 받기 V1 - @RequestParam
    @PostMapping("/add")
    public String add(@RequestParam String itemName,
                      @RequestParam int price,
                      @RequestParam int quantity, Model model) {

        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);

        return "basic/item";
    }
    */

    /**
     * PRG - Post -> Redirect -> Get
     */
    /**
     * @PostMapping("/add") public String add(@ModelAttribute("item") Item item) {
     * itemRepository.save(item);
     * return "redirect:/basic/items/" + item.getId();
     * }
     */

    @PostMapping("/add")
    public String add(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes) {
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true); // 저장 여부를 알려주는 변수

        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item updateParam) {
        itemRepository.update(itemId, updateParam);
        return "redirect:/basic/items/{itemId}";
    }
}
