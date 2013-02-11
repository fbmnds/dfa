(ns dfa.core)


(defn dfa-seq [dfa]
  (let [join
        (fn [word letter]
          (clojure.string/join (concat word (str letter))))
        process-curr-state
        (fn [[word curr-state] [letter new-state]]
          [(join word letter) new-state])
        process-states
        (fn [x]
          (if (empty? x)
            nil
            (let [[word state] x
                  new-states (map #(process-curr-state [word state] %)
                                  (state (:transitions dfa)))]
              [(map first (filter #((second %) (:accepts dfa)) new-states))
               new-states])))]
    (loop [current-states (list ["" (:start dfa)])
           processed-current-states (map process-states current-states)
           result-states ()
           i 20]
      (if (< i 0)
        result-states
        (recur (reduce concat (map second processed-current-states))
               (map process-states current-states)
               (set (reduce conj result-states
                            (apply concat
                                   (filter #(not (empty? %))
                                           (map first processed-current-states)))))
               (dec i))))))
