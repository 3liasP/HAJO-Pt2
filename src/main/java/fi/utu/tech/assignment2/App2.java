package fi.utu.tech.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class App2 {

    public static void main(String[] args) {
        List<Integer> sharedList = new ArrayList<>();

        // Luodaan ja käynnistetään threadCount verran laskijasäikeitä
        int threadCount = 20000;
        List<ListEditor> counters = Stream.generate(() -> new ListEditor(sharedList)).limit(threadCount).toList();
        counters.forEach(c -> c.start());
        counters.forEach(c -> {
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
         
        System.out.printf("Got %d, expected %d%n", sharedList.size(), threadCount);
    }
}


class ListEditor extends Thread {

    List<Integer> l;

    public ListEditor(List<Integer> l) {
        this.l = l;
    }

    @Override
    public void run() {
        l.add(123);
    }
}