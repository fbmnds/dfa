(ns dfa.core)


(defn- join [word letter]
  (clojure.string/join (concat word (str letter))))

(defn do-transform-payload [payload-0 payload-1]
  (join payload-0 payload-1))

(defn set-fn-transform-payload [f]
  (def do-transform-payload f))

(defn do-transform-state [[payload-0 state-0] [payload-1 state-1]]
  [(do-transform-payload payload-0 payload-1) state-1])

(defn do-cycle-states [dfa current-states]
  (if (empty? current-states)
    nil
    (apply concat
           (map #((second %) (:transitions dfa))
                current-states))))

(defn dfa-seq- [dfa]
  (loop [current-states (list [nil (:start dfa)])
         processed-current-states (map #(do-cycle-states dfa %) current-states)
         result-states ()
         i 20]
    (if (< i 0)
      result-states
      (recur (reduce concat (map second processed-current-states))
             (map #(do-cycle-states dfa %) current-states)
             (set (reduce conj result-states
                          (apply concat
                                 (filter #(not (empty? %))
                                         (map first processed-current-states)))))
             (dec i)))))

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
