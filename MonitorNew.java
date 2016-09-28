package lesson8.hw.MonitorNew;
import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

public class MonitorNew {

        private String path;
        private int timeout;
        private ArrayList<String> prev = new ArrayList<String>();
        private ArrayList<Long> prevMod = new ArrayList<Long>();
        private ArrayList<String> curr = new ArrayList<String>();
        private ArrayList<Long> currMod = new ArrayList<Long>();
        private IFileEventsPlus events;

        public MonitorNew(String path) {
            this.path = path;
            createArray(prev, prevMod);
        }

        public void start() {
            while (true) {
                createArray(curr, currMod);
                compareArrays(prev, curr, prevMod, currMod);
                prev.clear();
                prevMod.clear();
                prev.addAll(curr);
                prevMod.addAll(currMod);

                System.out.println("Waiting...");
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException ex) {
                    return;
                }
            }
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int value) {
            timeout = value;
        }

        public IFileEventsPlus getEvents() {
            return events;
        }

        public void setEvents(IFileEventsPlus value) {
            events = value;
        }

        private void doFileAdded(String path) {
            if (events != null)
                events.onFileAdded(path);
        }

        private void doFileDeleted(String path) {
            if (events != null)
                events.onFileDeleted(path);
        }

        private void doFileChanged(String path) {
            if (events != null)
                events.onFileChanged(path);
        }

        private void compareArrays(ArrayList<String> m1, ArrayList<String> m2, ArrayList<Long> mod1, ArrayList<Long> mod2) {
            Iterator<String> it = m1.iterator();
            String path;

            while (it.hasNext()) {
                path = it.next();

                if ( ! m2.contains(path))
                    doFileDeleted(path);
            }


            it = m2.iterator();
            while (it.hasNext()) {
                path = it.next();

                if ( ! m1.contains(path)) {
                    doFileAdded(path);
                }

            }

            for (String p: m2) {
                if (m1.contains(p)) {
                    long time1 = mod1.get(m1.indexOf(p));
                    long time2 = mod2.get(m2.indexOf(p));
                    if(time2 != time1) {
                        doFileChanged(p);
                    }
                }
            }
        }

        private void createArray(ArrayList<String> output, ArrayList<Long> modify) {
            try {
                File file = new File(path);
                File[] list = file.listFiles();

                output.clear();
                modify.clear();
                for (File f : list) {
                    output.add(f.getCanonicalPath());
                    modify.add(f.lastModified());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }