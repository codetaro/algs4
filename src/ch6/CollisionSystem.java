package ch6;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Particle;
import edu.princeton.cs.algs4.StdDraw;

public class CollisionSystem {

    private class Event implements Comparable<Event> {
        private final double time;
        private final Particle a, b;
        private final int countA, countB;

        public Event(double t, Particle a, Particle b) {
            this.time = t;
            this.a = a;
            this.b = b;
            if (a != null) countA = a.count(); else countA = -1;
            if (b != null) countB = b.count(); else countB = -1;
        }

        @Override
        public int compareTo(Event that) {
            if      (this.time < that.time) return -1;
            else if (this.time > that.time) return +1;
            else return 0;
        }

        // 如果在事件进入优先队列和离开优先队列的这段时间内任何计数器发生了变化，这个事件就失效了
        public boolean isValid() {
            if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
        }
    }

    private MinPQ<Event> pq;        // 优先队列
    private double t = 0.0;         // 模拟时钟
    private Particle[] particles;   // 粒子数组

    public CollisionSystem(Particle[] particles) {
        this.particles = particles;
    }

    private void predictCollisions(Particle a, double limit) {
        if (a == null) return;
        // 将与particles[i]发生碰撞的事件插入pq中
        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= limit)
                pq.insert(new Event(t + dt, a, particles[i]));
        }
        double dtX = a.timeToHitVerticalWall();
        if (t + dtX <= limit)
            pq.insert(new Event(t + dtX, a, null));
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtY <= limit)
            pq.insert(new Event(t + dtY, null, a));
    }

    // 重绘事件:重新画出所有粒子(a 和 b 均为空)
    public void redraw(double limit, double Hz) {
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++) particles[i].draw();
        StdDraw.show(20);
        if (t < limit)
            pq.insert(new Event(t + 1.0 / Hz, null, null));
    }

    public void simulate(double limit, double Hz) {
        pq = new MinPQ<>();
        // 1.用所有粒子预测的所有未来碰撞初始化优先队列
        for (int i = 0; i < particles.length; i++)
            predictCollisions(particles[i], limit);
        pq.insert(new Event(0, null, null));   // 添加重绘事件
        while (!pq.isEmpty()) {
            // 2.处理一个事件
            Event event = pq.delMin();                  // 2.1 从队列中取出一个事件
            if (!event.isValid()) continue;
            for (int i = 0; i < particles.length; i++)
                particles[i].move(event.time - t);  // 2.2 更新时间和粒子的位置
            t = event.time;
            Particle a = event.a, b = event.b;
            if (a != null && b != null) a.bounceOff(b); // 2.3 处理碰撞
            else if (a != null && b == null) a.bounceOffVerticalWall();
            else if (a == null && b != null) b.bounceOffHorizontalWall();
            else if (a == null && b == null) redraw(limit, Hz);
            predictCollisions(a, limit);                // 2.4 加入所有新的潜在碰撞
            predictCollisions(b, limit);
        }
    }

    public static void main(String[] args) {
        StdDraw.show(0);
        int N = Integer.parseInt(args[0]);
        Particle[] particles = new Particle[N];

        for (int i = 0; i < N; i++)
            particles[i] = new Particle();
        CollisionSystem system = new CollisionSystem(particles);
        system.simulate(10000, 0.5);
    }
}
