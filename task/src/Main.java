import java.util.*;

// Prototype pattern
abstract class Book implements Cloneable {
    protected String title;
    protected String author;
    protected int pages;

    public abstract void read();

    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }
}

class Novel extends Book {
    public Novel() {
        this.title = "Unknown Novel";
        this.author = "Unknown Author";
        this.pages = 0;
    }

    @Override
    public void read() {
        System.out.println("Reading a novel: " + title + " by " + author);
    }
}

class Comic extends Book {
    public Comic() {
        this.title = "Unknown Comic";
        this.author = "Unknown Author";
        this.pages = 0;
    }

    @Override
    public void read() {
        System.out.println("Reading a comic: " + title + " by " + author);
    }
}

class BookCache {
    private static Map<String, Book> bookMap = new HashMap<>();

    public static Book getBook(String bookId) {
        Book cachedBook = bookMap.get(bookId);
        return (Book) cachedBook.clone();
    }

    public static void loadCache() {
        Novel novel = new Novel();
        novel.title = "To Kill a Mockingbird";
        novel.author = "Harper Lee";
        novel.pages = 281;
        bookMap.put("1", novel);

        Comic comic = new Comic();
        comic.title = "Batman: Year One";
        comic.author = "Frank Miller";
        comic.pages = 144;
        bookMap.put("2", comic);
    }
}

// Decorator pattern
abstract class BookDecorator extends Book {
    protected Book decoratedBook;

    public BookDecorator(Book decoratedBook) {
        this.decoratedBook = decoratedBook;
    }

    @Override
    public void read() {
        decoratedBook.read();
    }

    @Override
    public String getTitle() {
        return decoratedBook.getTitle();
    }

    @Override
    public String getAuthor() {
        return decoratedBook.getAuthor();
    }

    @Override
    public int getPages() {
        return decoratedBook.getPages();
    }
}

class Hardcover extends BookDecorator {
    public Hardcover(Book decoratedBook) {
        super(decoratedBook);
    }

    @Override
    public void read() {
        decoratedBook.read();
        addHardcover();
    }

    private void addHardcover() {
        System.out.println("This is a hardcover edition.");
    }
}

class SignedEdition extends BookDecorator {
    public SignedEdition(Book decoratedBook) {
        super(decoratedBook);
    }

    @Override
    public void read() {
        decoratedBook.read();
        addSignature();
    }

    private void addSignature() {
        System.out.println("This book is signed by the author.");
    }
}

// Iterator pattern
class BookCollection implements Iterable<Book> {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public Iterator<Book> iterator() {
        return books.iterator();
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        // Load book prototypes
        BookCache.loadCache();

        // Create new books using prototype
        Book clonedNovel = BookCache.getBook("1");
        Book clonedComic = BookCache.getBook("2");

        // Decorate books
        Book hardcoverNovel = new Hardcover(clonedNovel);
        Book signedComic = new SignedEdition(clonedComic);

        // Add books to collection
        BookCollection collection = new BookCollection();
        collection.addBook(hardcoverNovel);
        collection.addBook(signedComic);

        // Iterate and display books
        for (Book book : collection) {
            book.read();
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Pages: " + book.getPages());
            System.out.println();
        }
    }
}
