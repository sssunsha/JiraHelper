package com.hybris.caas.component;

import com.hybris.caas.model.GithubService;
import com.hybris.caas.model.ReleaseNote;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ReleaseNoteHelper {

    public  void start(Map<String, ReleaseNote> map, Map<String, GithubService> serviceMap,
                       String releaseReportFileLocation, StringBuilder releseReportStringBuilder) {

        // generate the new release note map based on repository
        // here the map key is the repository
        Map<String, List<ReleaseNote>> releaseMap = new HashMap<>();
        map.forEach((key, value) -> {
            if (releaseMap.containsKey(value.repository)) {
                List<ReleaseNote> rnList = releaseMap.get(value.repository);
                rnList.add(value);
                releaseMap.put(value.repository, rnList);
            } else {
                List rns = new ArrayList();
                rns.add(value);
                releaseMap.put(value.repository, rns);
            }
        });

        // write the release note report both to the file and StringBuilder
        try {
            OutputStream f = new FileOutputStream(releaseReportFileLocation);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(f));
            releaseMap.forEach((key, value) -> {
                try {
                    AtomicReference<Integer> index = new AtomicReference<>(1);
                    writer.write("# "+key + " (" + serviceMap.get(key).sha1 + ")");
                    releseReportStringBuilder.append("# "+key + " (" + serviceMap.get(key).sha1 + ")");
                    writer.newLine();
                    releseReportStringBuilder.append("\n");
                    value.sort(Comparator.reverseOrder());
                    value.forEach(v -> {
                        try {
                            writer.write(index.toString() + ". " + v.type + ": " + v.description);
                            releseReportStringBuilder.append(index.toString() + ". " + v.type + ": " + v.description);
                            writer.newLine();
                            releseReportStringBuilder.append("\n");
                            index.updateAndGet(v1 -> v1 + 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.newLine();
                    releseReportStringBuilder.append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
