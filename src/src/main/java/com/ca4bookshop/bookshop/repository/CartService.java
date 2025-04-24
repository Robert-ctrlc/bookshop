package com.ca4bookshop.bookshop.repository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    public void addItemToCart(Long bookId, int quantity) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartRepository.save(cartItem);
    }

    public void removeItemFromCart(Long bookId) {
        CartItem cartItem = cartRepository.findByBookId(bookId);
        cartRepository.delete(cartItem);
    }

    public List<CartItem> getCartItems() {
        return cartRepository.findAll();  

    public void checkout() {
        
    }
}
