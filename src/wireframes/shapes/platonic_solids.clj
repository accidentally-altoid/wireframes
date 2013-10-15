(ns wireframes.shapes.platonic-solids
  (:require [wireframes.shapes.primitives :as p]
            [wireframes.transform :as t]))

(def √2 (Math/sqrt 2))
(def √5 (Math/sqrt 5))

(def φ (/ (inc √5) 2))
(def -φ (- φ))

(def tetrahedron
  {:points [(t/point  1  0 (/ -1 √2))
            (t/point -1  0 (/ -1 √2))
            (t/point  0  1 (/  1 √2))
            (t/point  0 -1 (/  1 √2))]
   :polygons [[0 1 2] [0 2 3] [0 1 3] [1 2 3]]})


(def cube
  "Start with a point, extrude to a line alone the Z-plane, then extrude that
   line in the Y-axis to make a square... extrude again along the X-axis to
   complete the square. "
  (->
    (p/make-point 0 0 0)
    (p/extrude (t/translate 0 0 1) 1)
    (p/extrude (t/translate 0 1 0) 1)
    (p/extrude (t/translate 1 0 0) 1)))

(def octahedron nil)

(def dodecahedron nil)

(def icosahedron
  "A 20-sided polyhedron"
  (let [points (vec
                 (apply concat
                   (for [one [-1 1] rho [φ -φ]]
                     [(t/point 0 one rho)
                      (t/point one rho 0)
                      (t/point rho 0 one)])))
        polygons (vec
                (for [a (range (count points))
                      b (range a)
                      c (range b)
                      :when (and
                              (= (t/distance (points a) (points b)) 2.0)
                              (= (t/distance (points a) (points c)) 2.0)
                              (= (t/distance (points b) (points c)) 2.0))]
                  [a b c]))]
  {:points points
   :polygons polygons }))
