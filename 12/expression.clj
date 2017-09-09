(defn constant [value] (fn [m] value))
(defn variable [name] (fn [m] (m name)))
(defn evaluate [op operations] (fn [m]
                                 (loop [res ((first operations) m) args (next operations)]
                                   (if (nil? args)
                                     res
                                     (recur (op res ((first args) m)) (next args))))))
(defn add [& operations] (evaluate + operations))
(defn subtract [& operations] (evaluate - operations))
(defn multiply [& operations] (evaluate * operations))
(defn divide [op1 op2] (fn [m]
                         (try (/ (op1 m) (op2 m)) (catch Exception e (* (op1 m) Double/POSITIVE_INFINITY)))))
(defn negate [operation] (fn [m] (- (operation m))))
(defn sin [operation] (fn [m] (Math/sin (operation m))))
(defn cos [operation] (fn [m] (Math/cos (operation m))))
(def m {'+ add
        '- subtract
        '* multiply
        '/ divide
        'negate negate
        'sin sin
        'cos cos})
(defn parse [expression] (if (number? expression)
                           (constant expression)
                           (if (symbol? expression)
                             (variable (name expression))
                             (apply (m (first expression)) (map parse (rest expression))))))
(defn parseFunction [str] (parse (read-string str)))
(def expr (parseFunction "(+ 2 x)"))
(println(expr {"x" 5 "y" 2 "z" 9}))