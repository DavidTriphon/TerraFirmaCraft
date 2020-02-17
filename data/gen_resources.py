import mcresources as mcr

import data.constants as tfc


def manual_blockstate_multipart(
        rsrc_mngr: mcr.ResourceManager,
        name_parts: mcr.utils.Sequence[str],
        parts: mcr.utils.Sequence[mcr.utils.Json]
):
    mcr.utils.write(
        (*rsrc_mngr.resource_dir, 'assets', rsrc_mngr.domain, 'blockstates', *mcr.utils.str_path(name_parts)),
        {'multipart': parts},
        rsrc_mngr.indent
    )


def main():
    mcr.clean_generated_resources('../src/main/resources')

    rm = mcr.ResourceManager('tfc', resource_dir='../src/main/resources')

    # Rock block variants
    for rock in tfc.ROCKS:
        for block_type in tfc.ROCK_BLOCK_TYPES:
            rm.blockstate(('rock', block_type, rock))
            rm.block_model(('rock', block_type, rock), 'tfc:block/rock/%s/%s' % (block_type, rock))
            rm.block_item_model(('rock', block_type, rock))
            rm.block_loot(('rock', block_type, rock), 'tfc:rock/%s/%s' % (block_type, rock))

    for rock, rock_data in tfc.ROCKS.items():
        rm.data(('tfc', 'rocks', rock), {
            'blocks': dict((block_type, 'tfc:rock/%s/%s' % (block_type, rock)) for block_type in tfc.ROCK_BLOCK_TYPES),
            **rock_data
        })

    # Sand
    for sand in tfc.SAND_BLOCK_TYPES:
        rm.blockstate(('sand', sand))
        rm.block_model(('sand', sand), textures='tfc:block/sand/%s' % sand)
        rm.block_item_model(('sand', sand))
        rm.block_loot(('sand', sand), 'tfc:sand/%s' % sand)

    # Dirt and Grass

    rm.block_model('north_tint', {}, 'block/block', [{
        'from': [0, 0, 0],
        'to': [16, 16, 0],
        'faces': {
            'north': {
                'texture': '#all',
                'cullface': 'north',
                'tintindex': 0
            }
        }
    }])

    # base Grass models
    rm.block_model(('soil', 'grass', 'side', 'template'), {'grass': 'tfc:block/soil/grass/side'}, 'block/block', [{
        'from': [0, 0, 0],
        'to': [16, 16, 0],
        'faces': {'north': {'texture': '#dirt', 'cullface': 'north'}}
    }, {
        'from': [0, 0, 0],
        'to': [16, 16, 0],
        'faces': {
            'north': {'texture': '#grass', 'cullface': 'north', 'tintindex': 0}
        }
    }])
    rm.block_model(('soil', 'grass', 'center', 'template'), {'grass': 'tfc:block/soil/grass/top'}, 'block/block', [{
        'from': [0, 0, 0],
        'to': [16, 16, 16],
        'faces': {
            'up': {'texture': '#grass', 'cullface': 'up', 'tintindex': 0},
            'down': {'texture': '#dirt', 'cullface': 'down'}
        }
    }])
    rm.block_model(('soil', 'grass', 'blend'), {'grass': 'tfc:block/soil/grass/top'}, 'block/block', [{
        'from': [0, 0, 0],
        'to': [16, 16, 0],
        'faces': {'north': {'texture': '#grass', 'cullface': 'north', 'tintindex': 0}}
    }])

    # base grass_path model
    rm.block_model(('soil', 'grass_path', 'template'), {'particle': '#bottom'}, 'block/block', [{
        'from': [0, 0, 0],
        'to': [16, 15, 16],
        'faces': {
            'down': {'uv': [0, 0, 16, 16], 'texture': '#bottom', 'cullface': 'down'},
            'up': {'uv': [0, 0, 16, 16], 'texture': '#top'},
            'north': {'uv': [0, 1, 16, 16], 'texture': '#side', 'cullface': 'north'},
            'south': {'uv': [0, 1, 16, 16], 'texture': '#side', 'cullface': 'south'},
            'west': {'uv': [0, 1, 16, 16], 'texture': '#side', 'cullface': 'west'},
            'east': {'uv': [0, 1, 16, 16], 'texture': '#side', 'cullface': 'east'}
        }
    }])

    # dry_grass models
    rm.block_model(('soil', 'dry_grass', 'base', 'template'), {
        'particle': '#dirt',
        'grass': 'tfc:block/soil/dry_grass/top'
    }, 'block/block', [{
        'from': [0, 0, 0],
        'to': [16, 16, 16],
        'faces': {
            'down': {'texture': '#dirt', 'cullface': 'down'},
            'up': {'texture': '#dirt', 'cullface': 'up'},
            'north': {'texture': '#dirt', 'cullface': 'north'},
            'south': {'texture': '#dirt', 'cullface': 'south'},
            'west': {'texture': '#dirt', 'cullface': 'west'},
            'east': {'texture': '#dirt', 'cullface': 'east'}
        }
    }, {
        'from': [0, 16, 0],
        'to': [16, 16, 16],
        'faces': {'up': {'texture': '#grass', 'cullface': 'up', 'tintindex': 0}}
    }])
    rm.block_model(('soil', 'dry_grass', 'blend'), {'all': 'tfc:block/soil/dry_grass/top'}, 'tfc:block/north_tint')
    rm.block_model(('soil', 'dry_grass', 'side'), {'all': 'tfc:block/soil/dry_grass/side'}, 'tfc:block/north_tint')

    # Soil Variants
    for soilVariant in tfc.SOIL_VARIANTS:
        # dirt block cube models
        rm.block_model(('soil', 'dirt', soilVariant), 'tfc:block/soil/dirt/%s' % soilVariant)

        # path block models
        rm.block_model(('soil', 'grass_path', soilVariant), {
            'side': 'tfc:block/soil/grass_path/side/%s' % soilVariant,
            'top': 'tfc:block/soil/grass_path/top/%s' % soilVariant,
            'bottom': 'tfc:block/soil/dirt/%s' % soilVariant
        }, 'tfc:block/soil/grass_path/template')

        # grass block models
        rm.block_model(('soil', 'grass', 'center', soilVariant), {
            'dirt': 'tfc:block/soil/dirt/%s' % soilVariant
        }, 'tfc:block/soil/grass/center/template')
        rm.block_model(('soil', 'grass', 'side', soilVariant), {
            'dirt': 'tfc:block/soil/dirt/%s' % soilVariant
        }, 'tfc:block/soil/grass/side/template')

        # grass block states
        manual_blockstate_multipart(
            rm,
            ('soil', 'grass', soilVariant), [
                *[{
                    # always apply grass column
                    'apply': [
                        {'model': 'tfc:block/soil/grass/center/%s' % soilVariant},
                        {'model': 'tfc:block/soil/grass/center/%s' % soilVariant, 'y': 90},
                        {'model': 'tfc:block/soil/grass/center/%s' % soilVariant, 'y': 180},
                        {'model': 'tfc:block/soil/grass/center/%s' % soilVariant, 'y': 270}
                    ]
                }], *[{
                    # apply grass top on the side when true
                    'when': {face: True},
                    'apply': {'model': 'tfc:block/soil/grass/blend', 'y': tfc.HORIZONTAL_ROTATIONS[face]}
                } for face in tfc.HORIZONTAL_DIRECTIONS], *[{
                    # apply grass side and dirt side when false
                    'when': {face: False},
                    'apply': {
                        'model': 'tfc:block/soil/grass/side/%s' % soilVariant,
                        'y': tfc.HORIZONTAL_ROTATIONS[face]
                    }
                } for face in tfc.HORIZONTAL_DIRECTIONS]
            ]
        )

        # dry grass block models
        rm.block_model(('soil', 'dry_grass', 'base', soilVariant), {
            'dirt': 'tfc:block/soil/dirt/%s' % soilVariant
        }, 'tfc:block/soil/dry_grass/base/template')

        # dry grass block states
        manual_blockstate_multipart(
            rm,
            ('soil', 'dry_grass', soilVariant), [
                *[{
                    # always apply dry grass column
                    'apply': [
                        {'model': 'tfc:block/soil/dry_grass/base/%s' % soilVariant},
                        {'model': 'tfc:block/soil/dry_grass/base/%s' % soilVariant, 'y': 90},
                        {'model': 'tfc:block/soil/dry_grass/base/%s' % soilVariant, 'y': 180},
                        {'model': 'tfc:block/soil/dry_grass/base/%s' % soilVariant, 'y': 270}
                    ]
                }], *[{
                    # apply grass top on the side when true
                    'when': {face: True},
                    'apply': {'model': 'tfc:block/soil/dry_grass/blend', 'y': tfc.HORIZONTAL_ROTATIONS[face]}
                } for face in tfc.HORIZONTAL_DIRECTIONS], *[{
                    # apply grass side when false
                    'when': {face: False},
                    'apply': {'model': 'tfc:block/soil/dry_grass/side', 'y': tfc.HORIZONTAL_ROTATIONS[face]}
                } for face in tfc.HORIZONTAL_DIRECTIONS]
            ]
        )

        # Dirt and Grass Path states
        for blockType in tfc.DIRT_BLOCK_TYPES:
            manual_blockstate_multipart(
                rm,
                ('soil', blockType, soilVariant), [{
                    'apply': [{
                        'model': 'tfc:block/soil/%s/%s' % (blockType, soilVariant), 'y': tfc.HORIZONTAL_ROTATIONS[face]
                    } for face in tfc.HORIZONTAL_DIRECTIONS]
                }]
            )

        # item models and block loots for all dirt blocks
        for blockType in tfc.DIRT_BLOCK_TYPES + tfc.GRASS_BLOCK_TYPES:
            rm.block_item_model(('soil', blockType, soilVariant))
            rm.block_loot((blockType, soilVariant), 'tfc:soil/%s/%s' % (blockType, soilVariant))


if __name__ == '__main__':
    main()
