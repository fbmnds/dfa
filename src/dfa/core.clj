(ns dfa.core)


(defn- join [word letter]
  (clojure.string/join (concat (str word) (str letter))))


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
           (map #(map (fn [x] (do-transform-state % x)) ((second %) (:transitions dfa)))
                current-states))))


(defn make-dfa-seq [dfa init-states]
  (if (empty? init-states)
    nil
    (lazy-seq
     (concat init-states
            (make-dfa-seq dfa
                          (do-cycle-states dfa init-states))))))


(defn dfa-seq [dfa]
  (let [init-states (list [nil (:start dfa)])]
    (filter #(not (nil? %)) (concat
               (map first
                    (filter #((second %) (:accepts dfa))
                            (make-dfa-seq dfa init-states)))))))
