HORIZONTAL_DIRECTIONS = ['east', 'west', 'north', 'south']
HORIZONTAL_ROTATIONS = {'east': 90, 'south': 180, 'west': 270, 'north': 0}

ROCKS = {
    'chalk': {
        'category': 'sedimentary'
    },
    'chert': {
        'category': 'sedimentary'
    },
    'claystone': {
        'category': 'sedimentary'
    },
    'conglomerate': {
        'category': 'sedimentary'
    },
    'dolomite': {
        'category': 'sedimentary'
    },
    'limestone': {
        'category': 'sedimentary'
    },
    'rocksalt': {
        'category': 'sedimentary'
    },
    'shale': {
        'category': 'sedimentary'
    },
    'gneiss': {
        'category': 'metamorphic'
    },
    'marble': {
        'category': 'metamorphic'
    },
    'phyllite': {
        'category': 'metamorphic'
    },
    'quartzite': {
        'category': 'metamorphic'
    },
    'schist': {
        'category': 'metamorphic'
    },
    'slate': {
        'category': 'metamorphic'
    },
    'diorite': {
        'category': 'igneous_intrusive'
    },
    'gabbro': {
        'category': 'igneous_intrusive'
    },
    'granite': {
        'category': 'igneous_intrusive'
    },
    'andesite': {
        'category': 'igneous_extrusive'
    },
    'basalt': {
        'category': 'igneous_extrusive'
    },
    'dacite': {
        'category': 'igneous_extrusive'
    },
    'rhyolite': {
        'category': 'igneous_extrusive'
    }
}
ROCK_BLOCK_TYPES = ['raw', 'bricks', 'cobble', 'gravel', 'smooth']
SAND_BLOCK_TYPES = ['brown', 'white', 'black', 'red', 'yellow', 'gray']
SOIL_VARIANTS = ['silty', 'sandy', 'loamy', 'clay', 'peat']

DIRT_BLOCK_TYPES = ['dirt', 'grass_path']
GRASS_BLOCK_TYPES = ['grass', 'dry_grass']
SOIL_BLOCK_TYPES = GRASS_BLOCK_TYPES + DIRT_BLOCK_TYPES
