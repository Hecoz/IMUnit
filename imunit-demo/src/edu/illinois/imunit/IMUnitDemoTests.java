package edu.illinois.imunit;

import static edu.illinois.imunit.IMUnit.fireEvent;
import static edu.illinois.imunit.IMUnit.schAssertEquals;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class demonstrates how multithreaded unit tests can be written using IMUnit.
 * 
 * 
 */
@RunWith(IMUnitRunner.class)
public class IMUnitDemoTests {

    private ArrayBlockingQueue<Integer> queue;

    @Before
    public void setup() {
        queue = new ArrayBlockingQueue<Integer>(1);
    }
/*
    @Test
    @Schedule("[startingTake]->finishOffer2")
    public void mytest1() throws InterruptedException {
        performParallelOfferssAndTake();
        assertEquals(1, queue.size());
    }
*/
    
    
    @Test
    @Schedule("finishOffer2->startingTake")
    public void testOfferOfferTake() throws InterruptedException {
        performParallelOfferssAndTake();
        assertEquals(0, queue.size());
    }

    @Test
    @Schedule("finishOffer1->startingTake,finishTake->startingOffer2")
    public void testOfferTakeOffer() throws InterruptedException {
        performParallelOfferssAndTake();
        assertEquals(1, queue.size());
    }

  
    
    @Test
    @Schedule("[startingTake]->startingOffer1,finishTake->startingOffer2")
    public void testTakeBlockOfferTakeFinishOffer() throws InterruptedException {
        performParallelOfferssAndTake();
        assertEquals(1, queue.size());
    }

    @Test
    @Schedules({ @Schedule(name = "offer-offer-take", value = "finishOffer2->startingTake"),
            @Schedule(name = "offer-take-offer", value = "finishOffer1->startingTake,finishTake->startingOffer2"),
            @Schedule(name = "takeBlock-offer-takeFinish-offer", value = "[startingTake]->startingOffer1,finishTake->startingOffer2") })
    public void testAllThreeSchedules() throws InterruptedException {
        performParallelOfferssAndTake();
        schAssertEquals("offer-offer-take", 0, queue.size());
        schAssertEquals("offer-take-offer", 1, queue.size());
        schAssertEquals("takeBlock-offer-takeFinish-offer", 1, queue.size());
    }
  
    private void performParallelOfferssAndTake() throws InterruptedException {
        Thread offerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                fireEvent("startingOffer1");
                queue.offer(43);
                System.out.println("offerred 1");
                fireEvent("finishOffer1");
                fireEvent("startingOffer2");
                queue.offer(47);
                System.out.println("offerred 2");
                fireEvent("finishOffer2");
            }
        });
        offerThread.start();
        fireEvent("startingTake");
        System.out.println(queue.size());
        assertEquals(43, (int) queue.take());
        System.out.println(queue.size());
        fireEvent("finishTake");
        offerThread.join();
    }

}
